package services.implementations;

import dao.jpa.PersonRepository;
import dao.jpa.UserRepository;
import dto.UserDTO;
import dto.converters.interfaces.UserConverter;
import entity.Person;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import validators.exceptions.general.InvalidTextException;
import validators.exceptions.users.UserAlreadyExists;
import validators.exceptions.users.UserNotFoundException;
import services.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import validators.impl.UserValidator;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PersonRepository personRepository;

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                UserDTO.builder()
                        .username(username)
                        .build()));
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        LOGGER.info("finding user by username " + username);
        return userConverter.convertEntityToDTO(
                userRepository
                        .findByUsername(username)
                        .orElseThrow( () -> new UserNotFoundException(
                                UserDTO.builder()
                                .username(username)
                                .build()
                        ) ));
    }

    @Override
    public UserDTO createUser(String username, String password, String fullName) {

        LOGGER.info("creating new person");
        final Person NEW_PERSON = Person.builder()
                .fullName(fullName)
                .build();

        LOGGER.info("adding new person to database");
        final Person ADDED_PERSON = personRepository.save(NEW_PERSON);

        LOGGER.info("creating new user");
        final User NEW_USER = User.builder()
                .username(username)
                .password(password)
                .userRole(Role.ROLE_USER)
                .person(ADDED_PERSON)
                .build();

        LOGGER.info("converting entity to dto");
        final UserDTO USER_DTO = userConverter.convertEntityToDTO(NEW_USER);

        LOGGER.info("checks username is exists");
        if (!userRepository.existsByUsername(username)) {
            LOGGER.info("adding user to database");
            return userConverter.convertEntityToDTO(userRepository.save(NEW_USER));
        } else {
            LOGGER.info("deleting new person");
            personRepository.delete(NEW_PERSON);
            throw new UserAlreadyExists(USER_DTO);
        }
    }
}

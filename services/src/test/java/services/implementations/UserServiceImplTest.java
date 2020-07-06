package services.implementations;

import dao.jpa.PersonRepository;
import dao.jpa.UserRepository;
import dto.PersonDTO;
import dto.UserDTO;
import dto.converters.interfaces.PersonConverter;
import dto.converters.interfaces.UserConverter;
import entity.Person;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import validators.interfaces.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {


    @Mock
    UserRepository userRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    UserConverter userConverter;
    @Mock
    PersonConverter personConverter;
    @InjectMocks
    UserServiceImpl userService;

    private PersonDTO personDTO = PersonDTO.builder()
            .fullName("alexei")
            .build();

    private Person personEntity = Person.builder()
            .fullName("alexei")
            .build();

    private UserDTO userDTO = UserDTO.builder()
            .id(4)
            .person(personDTO)
            .username("users")
            .build();

    private User userEntity = User.builder()
            .id(4)
            .person(personEntity)
            .username("users")
            .password("123")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(userConverter.convertDTOToEntity(userDTO)).thenReturn(userEntity);
        when(userConverter.convertEntityToDTO(userEntity)).thenReturn(userDTO);

    }

    @Test
    void loadUserByUsername() {
    }

//    @Test
//    void createUserSuccessfully() {
//        when(personRepository.save(personEntity)).thenReturn(personEntity);
//        when(userRepository.existsByUsername("users")).thenReturn(false);
//        when(userRepository.save(userEntity)).thenReturn(userEntity);
//        System.out.println(userEntity.toString());
//        assertEquals(userDTO, userService.createUser("users", "123", "alexei"));
//    }

    @Test
    void findUserByUsername() {
    }
}
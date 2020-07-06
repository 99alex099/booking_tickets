package validators.impl;

import dto.UserDTO;
import org.apache.log4j.Logger;
import validators.exceptions.users.UserNameIsIncorrectException;
import org.springframework.stereotype.Service;
import validators.interfaces.Validator;

@Service
public class UserValidator extends ValidatorImpl implements Validator<UserDTO> {

    private static final Logger LOGGER = Logger.getLogger(UserValidator.class);
    @Override
    public void isValid(UserDTO userDTO) {

        LOGGER.info("validator checks username");
        if (!nameIsValid(userDTO.getUsername())) {
            throw new UserNameIsIncorrectException(userDTO);
        }
    }

    private boolean nameIsValid(String name) {
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                if (symbolIsNotLetter(name.charAt(i))
                        && (symbolIsNotNumber(name.charAt(i)))) {
                            return false;
                        }
            }
            return true;
        } else {
            return false;
        }
    }
}

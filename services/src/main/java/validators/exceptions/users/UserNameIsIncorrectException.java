package validators.exceptions.users;

import dto.UserDTO;

public class UserNameIsIncorrectException extends UserException {

    public UserNameIsIncorrectException(UserDTO user) {
        super(user,
                "username " + user.getUsername() + " is incorrect: " +
                "username must be contains only letters and numbers");
    }
}

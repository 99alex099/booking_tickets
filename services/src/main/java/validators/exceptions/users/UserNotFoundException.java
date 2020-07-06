package validators.exceptions.users;

import dto.UserDTO;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(UserDTO user) {
        super(user,
                "user not found");
    }
}

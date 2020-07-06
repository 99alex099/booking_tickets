package validators.exceptions.users;

import dto.UserDTO;

public class UserAlreadyExists extends UserException {
    public UserAlreadyExists(UserDTO user) {
        super(user,
                "username " + user.getUsername() + "'s exists");
    }
}

package validators.exceptions.users;

import dto.UserDTO;
import lombok.Getter;

public class UserException extends RuntimeException {
    @Getter
    private UserDTO user;

    public UserException(UserDTO user, String message) {
        super(message);
        this.user = user;
    }
}

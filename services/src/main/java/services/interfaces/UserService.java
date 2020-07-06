package services.interfaces;

import dto.UserDTO;
import validators.exceptions.general.InvalidTextException;
import validators.exceptions.users.UserAlreadyExists;

public interface UserService {
    UserDTO findUserByUsername(String username);
    UserDTO createUser(String username, String password, String fullname) throws UserAlreadyExists,
            InvalidTextException;
}
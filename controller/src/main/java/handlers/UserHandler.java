package handlers;

import dto.ErrorDTO;
import dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.users.UserAlreadyExists;
import validators.exceptions.users.UserNameIsIncorrectException;
import validators.exceptions.users.UserNotFoundException;

@ControllerAdvice
public class UserHandler {
    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    ErrorDTO<UserDTO> userExists(final UserAlreadyExists e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.CONFLICT,
                e.getUser());
    }

    @ExceptionHandler(UserNameIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<UserDTO> nameIsIncorrect(final UserNameIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getUser());
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<UserDTO> userNotFound(final UserNotFoundException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getUser());
    }
}

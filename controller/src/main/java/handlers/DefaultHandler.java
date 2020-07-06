package handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dto.ErrorDTO;
import dto.FlightDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.flights.FlightIdNotFoundException;

//

@ControllerAdvice
public class DefaultHandler {
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO idIsIncorrect(final InvalidFormatException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                null);
    }

}

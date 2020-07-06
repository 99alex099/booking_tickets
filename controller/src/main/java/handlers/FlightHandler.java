package handlers;

import dto.CreateFlightRequestDTO;
import dto.ErrorDTO;
import dto.FlightDTO;
import dto.PlaneDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.flights.DateIsIncorrectException;
import validators.exceptions.flights.FlightCityNameIsIncorrectException;
import validators.exceptions.flights.FlightIdNotFoundException;
import validators.exceptions.flights.PriceException;

@ControllerAdvice
public class FlightHandler {
    @ExceptionHandler(FlightIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<FlightDTO> idIsIncorrect(final FlightIdNotFoundException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getFlight());
    }

    @ExceptionHandler(FlightCityNameIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<FlightDTO> nameIsIncorrect(final FlightCityNameIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getFlight());
    }
    @ExceptionHandler(DateIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<CreateFlightRequestDTO> dateIsIncorrect(final DateIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getCreateFlightRequestDTO());
    }
    @ExceptionHandler(PriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<CreateFlightRequestDTO> priceIsIncorrect(final PriceException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getCreateFlightRequestDTO());
    }
}

package handlers;

import dto.ErrorDTO;
import dto.PlaneDTO;
import dto.PlaneRowDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.planes.ModelNameIsIncorrectException;
import validators.exceptions.planes.PlaneIdNotFoundException;
import validators.exceptions.planeseats.BusinessClassNameIsNotSet;
import validators.exceptions.planeseats.CountOfSeatsIsIncorrect;
import validators.exceptions.planeseats.PlaneSeatIdIsIncorrect;

@ControllerAdvice
public class PlaneHandler {
    @ExceptionHandler(ModelNameIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<PlaneDTO> modelNameIsIncorrect(final ModelNameIsIncorrectException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getPlane());
    }

    @ExceptionHandler(PlaneIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<PlaneDTO> idIsIncorrectException(final PlaneIdNotFoundException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getPlane());
    }

    @ExceptionHandler(BusinessClassNameIsNotSet.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<PlaneRowDTO> businessClassNameIsNotSet(final BusinessClassNameIsNotSet e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getPlaneRow());
    }
    @ExceptionHandler(CountOfSeatsIsIncorrect.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorDTO<PlaneRowDTO> countOfSeatsIsIncorrect(final CountOfSeatsIsIncorrect e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.BAD_REQUEST,
                e.getPlaneRow());
    }
    @ExceptionHandler(PlaneSeatIdIsIncorrect.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<PlaneRowDTO> planeSeatIdIsIncorrect(final PlaneSeatIdIsIncorrect e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getPlaneRow());
    }
}

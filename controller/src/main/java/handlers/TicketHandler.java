package handlers;

import dto.ErrorDTO;
import dto.ReviewDTO;
import dto.TicketDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import validators.exceptions.tickets.TicketIsNotAvailableException;
import validators.exceptions.tickets.TicketNotFoundException;
import validators.exceptions.tickets.UserIsNotOwnerException;

@ControllerAdvice
public class TicketHandler {
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorDTO<TicketDTO> ticketNotFound(final TicketNotFoundException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.NOT_FOUND,
                e.getTicket());
    }
    @ExceptionHandler(TicketIsNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    ErrorDTO<TicketDTO> ticketIsNotAvailable(final TicketIsNotAvailableException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.CONFLICT,
                e.getTicket());
    }
    @ExceptionHandler(UserIsNotOwnerException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    ErrorDTO<TicketDTO> userIsNotOwner(final UserIsNotOwnerException e) {
        return new ErrorDTO<>(e.getMessage(), HttpStatus.CONFLICT,
                e.getTicket());
    }
}

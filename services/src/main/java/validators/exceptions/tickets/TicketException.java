package validators.exceptions.tickets;

import dto.TicketDTO;
import lombok.Getter;

public class TicketException extends RuntimeException {
    @Getter
    private TicketDTO ticket;

    public TicketException(TicketDTO ticket, String message) {
        super(message);
        this.ticket = ticket;
    }
}

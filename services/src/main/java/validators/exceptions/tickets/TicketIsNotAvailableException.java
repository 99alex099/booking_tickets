package validators.exceptions.tickets;

import dto.TicketDTO;

public class TicketIsNotAvailableException extends TicketException {
    public TicketIsNotAvailableException(TicketDTO ticket) {
        super(ticket,
                "ticket is not available");
    }
}

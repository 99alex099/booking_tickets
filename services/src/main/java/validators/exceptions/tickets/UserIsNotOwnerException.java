package validators.exceptions.tickets;

import dto.TicketDTO;

public class UserIsNotOwnerException extends TicketException {
    public UserIsNotOwnerException(TicketDTO ticket) {
        super(ticket,
                "user isn't owner of ticket");
    }
}

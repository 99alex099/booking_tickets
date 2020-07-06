package validators.exceptions.tickets;

import dto.TicketDTO;

public class TicketNotFoundException extends TicketException {
    public TicketNotFoundException(TicketDTO ticket) {
        super(ticket, "Ticket not found");
    }

    public TicketNotFoundException(Integer id) {
        super(TicketDTO.builder()
                        .id(id)
                        .build(),
                "Ticket[id:" + id + "] not found");
    }
}

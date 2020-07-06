package controllers;

import dto.TicketDTO;
import services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketsController {
    private final TicketService ticketService;

    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("{id}")
    public TicketDTO getTicket(@PathVariable Integer id) {
        return ticketService.findTicket(id);
    }

    @DeleteMapping("{id}")
    public void freeTicket(@PathVariable Integer id) {

        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        ticketService.cancelBooking(id, name);
    }
}

package controllers;

import dto.CreateFlightRequestDTO;
import dto.TicketDTO;
import dto.FlightDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import services.interfaces.TicketService;
import services.interfaces.UserService;
import services.interfaces.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")

public class FlightController {
    private final FlightService flightService;
    private final TicketService ticketService;
    private final UserService userService;

    public FlightController(FlightService flightService,
                            TicketService ticketService,
                            UserService userService) {
        this.flightService = flightService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("{id}/tickets")
    public List<TicketDTO> getTickets(@PathVariable("id") Integer flightId) {
        return ticketService.findFreeTickets(flightId);
    }
    @PostMapping("{id}/tickets")
    public TicketDTO orderTicket(
            @RequestBody TicketDTO ticket,
            @PathVariable("id") Integer flightId) {
        ticket.setFlight(
                flightService.findFlight(flightId)
        );
        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ticketService.bookTicket(ticket,
                userService.findUserByUsername(name).getPerson());
    }

    @GetMapping
    public List<FlightDTO> getFlights(@RequestParam(value = "arrival_city", required = false) String arrivalCity,
                                      @RequestParam(value = "departure_city", required = false) String departureCity) {
        if (arrivalCity == null || departureCity == null) {
            return flightService.findFlights();
        } else {
            return flightService.findFlightsByCityName(
                    FlightDTO.builder()
                    .arrivalCity(arrivalCity)
                    .departureCity(departureCity)
                    .build()
            );
        }
    }

    @GetMapping
    @RequestMapping("{id}")
    public FlightDTO getFlight(@PathVariable int id) {
        return flightService.findFlight(id);
    }

    @DeleteMapping("{id}")
    public void deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlight(id);
    }

    @PutMapping("{id}")
    public FlightDTO setFlight(@RequestBody FlightDTO flight, @PathVariable Integer id) {
        flight.setId(id);
        return flightService.updateFlight(flight);
    }

    @PostMapping()
    public FlightDTO addFlight(
            @RequestBody CreateFlightRequestDTO createFlightRequestDTO) {
        return flightService.addFlight(createFlightRequestDTO);
    }
}
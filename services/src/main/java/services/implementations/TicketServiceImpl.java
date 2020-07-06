package services.implementations;

import dao.jpa.FlightRepository;
import dao.jpa.TicketRepository;
import dto.PersonDTO;
import dto.TicketDTO;
import dto.UserDTO;
import dto.converters.interfaces.PersonConverter;
import dto.converters.interfaces.TicketConverter;
import entity.Person;
import entity.Ticket;
import org.apache.log4j.Logger;
import services.interfaces.TicketService;
import services.interfaces.UserService;
import validators.exceptions.UserIsNotAuthorOfReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.exceptions.flights.FlightIdNotFoundException;
import validators.exceptions.tickets.TicketIsNotAvailableException;
import validators.exceptions.tickets.TicketNotFoundException;
import validators.exceptions.tickets.UserIsNotOwnerException;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final TicketConverter ticketConverter;
    private final PersonConverter personConverter;
    private final UserService userService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             FlightRepository flightRepository,
                             TicketConverter ticketConverter,
                             PersonConverter personConverter,
                             UserService userService) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.ticketConverter = ticketConverter;
        this.personConverter = personConverter;
        this.userService = userService;
    }

    @Override
    public TicketDTO findTicket(Integer id) {
        LOGGER.info("trying to find ticket[id:" + id + "]");
        return ticketConverter.convertEntityToDTO(
                ticketRepository.findById(id).orElseThrow(
                        () -> new TicketNotFoundException(id)
                ));
    }

    @Override
    public List<TicketDTO> findFreeTickets(int flightID) {
        if (flightRepository.existsById(flightID)) {
            LOGGER.info("trying to find tickets of flight[id:" + flightID + "]");
            return ticketConverter.
                    convertEntityToDTO(
                            ticketRepository.findAllByFlight_Id(flightID));

        } else {
            throw new FlightIdNotFoundException(flightID);
        }
    }

    @Override
    public TicketDTO bookTicket(TicketDTO ticket, PersonDTO owner) {
        LOGGER.info("trying to book ticket[" + ticket.getId() + "]");
        LOGGER.info("finding ticket[" + ticket.getId() + "] in database");
        Ticket entityTicket = ticketRepository.findByRowAndSeatNumberAndFlightId(
                ticket.getRow(),
                ticket.getSeatNumber(),
                ticket.getFlight().getId()
        ).orElseThrow(
                () -> new TicketNotFoundException(ticket)
        );
        LOGGER.info("checking ticket[" + ticket.getId() + "]: is available?");
        if (entityTicket.getOwner() == null) {

            LOGGER.info("trying convert ticket[" + ticket.getId() + "] to entity");

            entityTicket.setOwner(
                    personConverter.convertDTOToEntity(owner)
            );

            LOGGER.info(owner.getFullName() + " booked ticket " + ticket.toString());
            return ticketConverter.convertEntityToDTO(
                    ticketRepository.save(entityTicket)
            );
        } else {
            throw new TicketIsNotAvailableException(ticket);
        }
    }

    @Override
    public void cancelBooking(int id, String username) throws UserIsNotAuthorOfReviewException {
        UserDTO user = userService.findUserByUsername(username);

        Ticket ticket = ticketRepository.findByIdAndOwner(id,
                personConverter.convertDTOToEntity(user.getPerson())
        ).orElseThrow(
                () -> new UserIsNotOwnerException(
                        TicketDTO.builder()
                                .id(id)
                                .build()
                )
        );

        ticket.setOwner(null);

        ticketConverter.convertEntityToDTO(ticketRepository.save(ticket));
    }
}
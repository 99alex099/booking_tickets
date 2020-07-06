package services.implementations;

import dao.jpa.FlightRepository;
import dao.jpa.TicketRepository;
import dto.FlightDTO;
import dto.PersonDTO;
import dto.PlaneDTO;
import dto.TicketDTO;
import dto.converters.interfaces.PersonConverter;
import dto.converters.interfaces.TicketConverter;
import entity.Flight;
import entity.Person;
import entity.Plane;
import entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import validators.exceptions.flights.FlightIdNotFoundException;
import validators.exceptions.tickets.TicketIsNotAvailableException;
import validators.exceptions.tickets.TicketNotFoundException;
import validators.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TicketServiceImplTest {

    @Mock
    TicketRepository ticketRepository;
    @Mock
    TicketConverter ticketConverter;
    @Mock
    PersonConverter personConverter;
    @Mock
    FlightRepository flightRepository;
    @Mock
    Validator<TicketDTO> ticketValidator;
    @InjectMocks
    TicketServiceImpl ticketService;

    private Ticket ticketEntity = Ticket.builder()
            .id(4)
            .price(42)
            .row(1)
            .seatNumber(1)
            .flight(
                    Flight.builder()
                            .departureCity("Minsk")
                            .arrivalCity("Moscow")
                            .plane(
                                    Plane.builder()
                                            .modelName("boeing").
                                            build()
                            )
                    .build()
            )
            .build();

    private TicketDTO ticketDTO = TicketDTO.builder()
            .id(4)
            .price(42)
            .row(1)
            .seatNumber(1)
            .flight(
                    FlightDTO.builder()
                            .departureCity("Minsk")
                            .arrivalCity("Moscow")
                            .plane(
                                    PlaneDTO.builder()
                                            .modelName("boeing").
                                            build()
                            )
                            .build()
            )
            .build();
    private List<Ticket> ticketEntityList = new ArrayList<>();
    private List<TicketDTO> ticketDTOList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(ticketConverter.convertDTOToEntity(ticketDTOList)).thenReturn(ticketEntityList);
        when(ticketConverter.convertEntityToDTO(ticketEntityList)).thenReturn(ticketDTOList);

        when(ticketConverter.convertDTOToEntity(ticketDTO)).thenReturn(ticketEntity);
        when(ticketConverter.convertEntityToDTO(ticketEntity)).thenReturn(ticketDTO);
    }

    @Test
    void findTicketIsSuccessfully() {
        Optional<Ticket> ticketOptional = Optional.of(ticketEntity);

        when(ticketRepository.findById(4)).thenReturn(ticketOptional);

        assertEquals(ticketDTO, ticketService.findTicket(4));

    }

    @Test
    void findTicketIdIsIncorrect() {
        Optional<Ticket> ticketOptional = Optional.empty();

        when(ticketRepository.findById(4)).thenReturn(ticketOptional);

        try {
            ticketService.findTicket(4);
            fail("Expected TicketNotFoundException");
        } catch (TicketNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void findFreeTicketsFlightIdIsCorrect() {
        int flightID = 1;

        when(flightRepository.existsById(flightID)).thenReturn(true);
        when(ticketRepository.findAllByFlight_Id(flightID)).thenReturn(ticketEntityList);

        assertEquals(ticketDTOList, ticketService.findFreeTickets(flightID));
    }

    @Test
    void findFreeTicketsFlightIdIsIncorrect() {
        int flightID = 1;

        when(flightRepository.existsById(flightID)).thenReturn(false);
        when(ticketRepository.findAllByFlight_Id(flightID)).thenReturn(ticketEntityList);

        try {
            ticketService.findFreeTickets(flightID);
            fail("Expected FlightIdNotFoundException");
        } catch (FlightIdNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Expected FlightIdNotFoundException");
        }

    }

    @Test
    void bookTicketIsSuccessfully() {
        PersonDTO personDTO = PersonDTO.builder()
                .fullName("alexei")
                .build();

        Person personEntity = Person.builder()
                .fullName("alexei")
                .build();

        Optional<Ticket> ticketOptional = Optional.of(ticketEntity);

        when(personConverter.convertEntityToDTO(personEntity)).thenReturn(personDTO);
        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);



        when(ticketRepository.findByRowAndSeatNumberAndFlightId(
                ticketDTO.getRow(),
                ticketDTO.getSeatNumber(),
                ticketDTO.getFlight().getId()
        )).thenReturn(ticketOptional);

        assertEquals(ticketDTO, ticketService.bookTicket(ticketDTO, personDTO));
    }

    @Test
    void bookTicketWhenItNotFound() {
        PersonDTO personDTO = PersonDTO.builder()
                .fullName("alexei")
                .build();

        Person personEntity = Person.builder()
                .fullName("alexei")
                .build();

        Optional<Ticket> ticketOptional = Optional.empty();

        when(personConverter.convertEntityToDTO(personEntity)).thenReturn(personDTO);
        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);


        when(ticketRepository.findByRowAndSeatNumberAndFlightId(
                ticketDTO.getRow(),
                ticketDTO.getSeatNumber(),
                ticketDTO.getFlight().getId()
        ))
                .thenReturn(ticketOptional);
        try {
            ticketService.bookTicket(ticketDTO, personDTO);
        } catch (TicketNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void bookTicketWhenItIsNotFree() {
        PersonDTO personDTO = PersonDTO.builder()
                .fullName("alexei")
                .build();

        Person personEntity = Person.builder()
                .fullName("alexei")
                .build();

        TicketDTO freeTicket = TicketDTO.builder()
                .id(4)
                .price(42)
                .row(1)
                .seatNumber(1)
                .owner(personDTO)
                .flight(
                        FlightDTO.builder()
                                .departureCity("Minsk")
                                .arrivalCity("Moscow")
                                .plane(
                                        PlaneDTO.builder()
                                                .modelName("boeing").
                                                build()
                                )
                                .build()
                )
                .build();

        Optional<Ticket> ticketOptional = Optional.empty();

        when(personConverter.convertEntityToDTO(personEntity)).thenReturn(personDTO);
       // when(ticketRepository.save(freeTicket)).thenReturn(ticketEntity);


        when(ticketRepository.findByRowAndSeatNumberAndFlightId(
                ticketDTO.getRow(),
                ticketDTO.getSeatNumber(),
                ticketDTO.getFlight().getId()
        ))
                .thenReturn(ticketOptional);
        try {
            ticketService.bookTicket(freeTicket, personDTO);
        } catch (TicketIsNotAvailableException e) {
            assertTrue(true);
        }
    }
}
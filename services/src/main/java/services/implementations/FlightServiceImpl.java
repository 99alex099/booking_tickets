package services.implementations;

import dao.jpa.TicketRepository;
import dto.CreateFlightRequestDTO;
import dto.converters.interfaces.FlightConverter;
import dto.converters.interfaces.PersonConverter;
import dto.converters.interfaces.PlaneConverter;
import dto.converters.interfaces.PlaneRowConverter;
import entity.Flight;
import entity.Plane;
import entity.PlaneRow;
import entity.Ticket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import services.interfaces.FlightService;
import services.interfaces.PlaneService;
import org.springframework.stereotype.Service;
import dao.jpa.FlightRepository;
import dto.FlightDTO;
import validators.exceptions.flights.FlightIdNotFoundException;
import validators.interfaces.Validator;

import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final PlaneService planeService;
    private final TicketRepository ticketRepository;
    private final FlightConverter flightConverter;
    private final PlaneConverter planeConverter;
    private final PlaneRowConverter planeRowConverter;
    private final Validator<FlightDTO> flightValidator;
    private final Validator<CreateFlightRequestDTO> createFlightRequestDTOValidator;

    private static final Logger LOGGER = Logger.getLogger(FlightServiceImpl.class);

    public FlightServiceImpl(
            FlightRepository flightRepository,
            PlaneService planeService,
            TicketRepository ticketRepository,
            FlightConverter flightConverter,
            PlaneConverter planeConverter,
            PlaneRowConverter planeRowConverter,
            Validator<FlightDTO> flightValidator,
            Validator<CreateFlightRequestDTO> createFlightRequestDTOValidator) {

        this.flightRepository = flightRepository;
        this.planeService = planeService;
        this.ticketRepository = ticketRepository;
        this.flightConverter = flightConverter;
        this.planeConverter = planeConverter;
        this.planeRowConverter = planeRowConverter;
        this.flightValidator = flightValidator;
        this.createFlightRequestDTOValidator = createFlightRequestDTOValidator;
    }

    @Override
    public FlightDTO findFlight(Integer id) {
        LOGGER.info("finding flight with id " + id);
        Optional<Flight> flightOptional = flightRepository.findById(id);
        return flightConverter.convertEntityToDTO(flightOptional.orElseThrow(() -> new
                FlightIdNotFoundException(id)));
    }

    public List<FlightDTO> findFlights() {
        LOGGER.info("finding all flights");
        return flightConverter.convertEntityToDTO(flightRepository.findAll());
    }

    @Override
    public List<FlightDTO> findFlightsByCityName(FlightDTO flight) {
        LOGGER.info("finding flight with arrival city '" + flight.getArrivalCity()
                + "' and '" + flight.getDepartureCity() + "'");

        flightValidator.isValid(flight);

        return flightConverter.convertEntityToDTO(
                flightRepository.findFlightsByArrivalCityAndDepartureCity(
                        flight.getArrivalCity(), flight.getDepartureCity()
                ));
    }

    @Override
    public void deleteFlight(Integer id) {
        LOGGER.info("trying to delete flight[id:" + id + "]");
        flightRepository.deleteById(id);
    }

    @Override
    public FlightDTO addFlight(FlightDTO flight) {
        flightValidator.isValid(flight);
        LOGGER.info("trying to add a flight " + flight.getArrivalCity() + "-" + flight.getDepartureCity());
        return flightConverter.convertEntityToDTO(
                flightRepository.save(
                        flightConverter.convertDTOToEntity(flight)));
    }

    @Override
    public FlightDTO addFlight(CreateFlightRequestDTO createFlightRequestDTO) {

        createFlightRequestDTOValidator.isValid(createFlightRequestDTO);
        LOGGER.info("trying to add flight");
        FlightDTO tempFlight = FlightDTO.builder()
                .arrivalCity(createFlightRequestDTO.getArrivalCity())
                .departureCity(createFlightRequestDTO.getDepartureCity())
                .build();

        flightValidator.isValid(tempFlight);

        Flight flight = Flight.builder()
                .arrivalCity(createFlightRequestDTO.getArrivalCity())
                .departureCity(createFlightRequestDTO.getDepartureCity())
                .build();

        Calendar calendarArrival = new GregorianCalendar(
                createFlightRequestDTO.getArrivalYear(),
                createFlightRequestDTO.getArrivalMonth() - 1,
                createFlightRequestDTO.getArrivalDay());
        calendarArrival.set(Calendar.HOUR_OF_DAY, createFlightRequestDTO.getArrivalHour());
        calendarArrival.set(Calendar.MINUTE, createFlightRequestDTO.getArrivalMinute());
        Date arrivalDate = calendarArrival.getTime();

        Calendar calendarDeparture = new GregorianCalendar(
                createFlightRequestDTO.getDepartureYear(),
                createFlightRequestDTO.getDepartureMonth() - 1,
                createFlightRequestDTO.getDepartureDay());
        calendarDeparture.set(Calendar.HOUR_OF_DAY, createFlightRequestDTO.getDepartureHour());
        calendarDeparture.set(Calendar.MINUTE, createFlightRequestDTO.getDepartureMinute());
        Date departureDate = calendarDeparture.getTime();

        flight.setDepartureDateTime(departureDate);
        flight.setArrivalDateTime(arrivalDate);
        Plane flightPlane = planeConverter.convertDTOToEntity(
                planeService.findPlaneById(createFlightRequestDTO.getPlaneId()));

        flight.setPlane(flightPlane);

        final Flight addedFlight = flightConverter.convertDTOToEntity(
                addFlight(
                        flightConverter.convertEntityToDTO(flight)));

        List<PlaneRow> planeRowList = planeRowConverter.convertDTOToEntity(
                planeService.findPlaneSeats(
                        planeConverter.convertEntityToDTO(addedFlight.getPlane()))
        );

        List<Ticket> flightTickets = new LinkedList<>();

        for (int i = 0; i < planeRowList.size(); i++) {
            final PlaneRow planeRow = planeRowList.get(i);
            for (int seat = 1; seat <= planeRow.getCountOfSeats(); seat++) {
                flightTickets.add(new Ticket(addedFlight,
                        planeRow.isBusinessClass() ?
                                createFlightRequestDTO.getBusinessClassPrice()
                                : createFlightRequestDTO.getOtherPrice(),
                        i + 1, seat));
            }
        }

        LOGGER.info("trying to save flight tickets");
        ticketRepository.saveAll(flightTickets);

        return flightConverter.convertEntityToDTO(addedFlight);
    }

    @Override
    public FlightDTO updateFlight(FlightDTO flight) {
        flightValidator.isValid(flight);
        Flight flightEntity = flightConverter.convertDTOToEntity(flight);
        LOGGER.info("trying to update save");
        return flightConverter.convertEntityToDTO(
                flightRepository.save(flightEntity));
    }
}
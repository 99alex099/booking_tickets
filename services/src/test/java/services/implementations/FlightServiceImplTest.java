package services.implementations;

import dao.jpa.FlightRepository;
import dto.FlightDTO;
import dto.converters.interfaces.FlightConverter;
import entity.Flight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import validators.exceptions.flights.ArrivalCityNameIsIncorrectExceptionException;
import validators.exceptions.flights.DepartureCityNameIsInvalidExceptionException;
import validators.exceptions.flights.FlightIdNotFoundException;
import validators.impl.FlightValidator;
import validators.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FlightServiceImplTest {

    @Mock
    FlightRepository flightRepository;
    @Mock
    FlightConverter flightConverter;
    @Mock
    Validator<FlightDTO> flightValidator;
    @InjectMocks
    FlightServiceImpl flightService;

    private FlightDTO expectedFlightDTO = FlightDTO.builder()
            .id(5)
            .arrivalCity("Msc")
            .departureCity("Minsk")
            .build();

    private Flight expectedFlightEntity = Flight.builder()
            .id(5)
            .arrivalCity("Msc")
            .departureCity("Minsk")
            .build();

    @Before
    public void init() {
        System.out.println("CTAPT");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findFlightIsSuccessfully() {
        MockitoAnnotations.initMocks(this);

        Optional<Flight> flightOptional = Optional.of(expectedFlightEntity);
        when(flightRepository.findById(5)).thenReturn(flightOptional);
        when(flightConverter.convertEntityToDTO(flightOptional.get()))
                .thenReturn(expectedFlightDTO);
        assertEquals(flightService.findFlight(5), expectedFlightDTO);
    }
    @Test
    void findFlightIsNotSuccessfully() {
        MockitoAnnotations.initMocks(this);

        Optional<Flight> flightNotFound = Optional.empty();
        Optional<Flight> flightOptional = Optional.of(expectedFlightEntity);
        when(flightRepository.findById(5)).thenReturn(flightOptional);
        when(flightRepository.findById(7)).thenReturn(flightNotFound);

        try {
            flightService.findFlight(7);
            Assert.fail("Expected FlightIdNotFoundException");
        } catch (FlightIdNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void findFlights() {
        MockitoAnnotations.initMocks(this);

        List<Flight> flightsEntity = new ArrayList<>();
        flightsEntity.add(Flight.builder()
                .arrivalCity("Minsk")
                .departureCity("Moscow")
                .build());
        flightsEntity.add(Flight.builder()
                .arrivalCity("Moscow")
                .departureCity("Minsk")
                .build());
        List<FlightDTO> expectedFlightsDTO = new ArrayList<>();
        expectedFlightsDTO.add(FlightDTO.builder()
                .arrivalCity("Minsk")
                .departureCity("Moscow")
                .build());
        expectedFlightsDTO.add(FlightDTO.builder()
                .arrivalCity("Moscow")
                .departureCity("Minsk")
                .build());

        when(flightRepository.findAll())
                .thenReturn(flightsEntity);
        when(flightConverter.convertEntityToDTO(flightsEntity))
                .thenReturn(expectedFlightsDTO);
        assertEquals(flightService.findFlights(), expectedFlightsDTO);
    }

    @Test
    void testFindFlightsByCityDataIsCorrect() {
        MockitoAnnotations.initMocks(this);

        String arrival = "Minsk";
        String departure = "Moscow";

        Flight flightEntity =
                Flight.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        List<FlightDTO> expectedFlightsDTO = new ArrayList<>();
        expectedFlightsDTO.add(DTO);

        List<Flight> expectedFlightsEntity = new ArrayList<>();
        expectedFlightsEntity.add(flightEntity);

        when(flightRepository.findFlightsByArrivalCityAndDepartureCity(arrival, departure))
                .thenReturn(expectedFlightsEntity);
        when(flightConverter.convertEntityToDTO(expectedFlightsEntity))
                .thenReturn(expectedFlightsDTO);
        assertEquals(flightService.findFlightsByCityName(DTO),
                expectedFlightsDTO);
    }
    @Test
    void testFindFlightsByCityArrivalIsIncorrect() {
        MockitoAnnotations.initMocks(this);

        String arrival = "M1nsk";
        String departure = "Moscow";

        Flight flightEntity =
                Flight.builder().arrivalCity(arrival)
                .departureCity(departure)
                .build();

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                .departureCity(departure)
                .build();

        List<FlightDTO> expectedFlightsDTO = new ArrayList<>();
        expectedFlightsDTO.add(DTO);

        List<Flight> expectedFlightsEntity = new ArrayList<>();
        expectedFlightsEntity.add(flightEntity);

        doThrow(ArrivalCityNameIsIncorrectExceptionException.class)
                .when(flightValidator)
                .isValid(DTO);
        when(flightRepository.findFlightsByArrivalCityAndDepartureCity(arrival, departure))
                .thenReturn(expectedFlightsEntity);
        when(flightConverter.convertEntityToDTO(expectedFlightsEntity))
                .thenReturn(expectedFlightsDTO);
        try {
            flightService.findFlightsByCityName(DTO);
            Assert.fail("Expected ArrivalCityNameIsIncorrectException");
        } catch (ArrivalCityNameIsIncorrectExceptionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void testFindFlightsByCityDepartureIsIncorrect() {
        MockitoAnnotations.initMocks(this);

        String arrival = "Minsk";
        String departure = "M0scow";

        Flight flightEntity =
                Flight.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        List<FlightDTO> expectedFlightsDTO = new ArrayList<>();
        expectedFlightsDTO.add(DTO);

        List<Flight> expectedFlightsEntity = new ArrayList<>();
        expectedFlightsEntity.add(flightEntity);

        doThrow(DepartureCityNameIsInvalidExceptionException.class)
                .when(flightValidator)
                .isValid(DTO);
        when(flightRepository.findFlightsByArrivalCityAndDepartureCity(arrival, departure))
                .thenReturn(expectedFlightsEntity);
        when(flightConverter.convertEntityToDTO(expectedFlightsEntity))
                .thenReturn(expectedFlightsDTO);
        try {
            flightService.findFlightsByCityName(DTO);
            Assert.fail("Expected DepartureCityNameIsIncorrectException");
        } catch (DepartureCityNameIsInvalidExceptionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void testAddCityArrivalIsIncorrect() {
        MockitoAnnotations.initMocks(this);

        String arrival = "M1nsk";
        String departure = "Moscow";

        Flight flightEntity =
                Flight.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        doThrow(ArrivalCityNameIsIncorrectExceptionException.class)
                .when(flightValidator)
                .isValid(DTO);
        try {
            flightService.addFlight(DTO);
            Assert.fail("Expected ArrivalCityNameIsIncorrectException");
        } catch (ArrivalCityNameIsIncorrectExceptionException e) {
            Assert.assertTrue(true);
        }
    }
    @Test
    void testAddCityDepartureIsIncorrect() {
        MockitoAnnotations.initMocks(this);

        String arrival = "Minsk";
        String departure = "M0scow";

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        doThrow(DepartureCityNameIsInvalidExceptionException.class)
                .when(flightValidator)
                .isValid(DTO);
        try {
            flightService.addFlight(DTO);
            Assert.fail("Expected DepartureCityNameIsIncorrectException");
        } catch (DepartureCityNameIsInvalidExceptionException e) {
            Assert.assertTrue(true);
        }
    }
    @Test
    void testDeleteById() {
        MockitoAnnotations.initMocks(this);

        flightService.deleteFlight(4);
        verify(flightRepository).deleteById(4);
    }
    @Test
    void addFlightIsSuccessfully() {
        MockitoAnnotations.initMocks(this);

        String arrival = "Minsk";
        String departure = "M0scow";

        Flight flightEntity =
                Flight.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        FlightDTO DTO =
                FlightDTO.builder().arrivalCity(arrival)
                        .departureCity(departure)
                        .build();

        when(flightConverter.convertDTOToEntity(DTO)).thenReturn(flightEntity);
        when(flightConverter.convertEntityToDTO(flightEntity)).thenReturn(DTO);
        when(flightRepository.save(flightEntity)).thenReturn(flightEntity);

        assertEquals(DTO, flightService.addFlight(DTO));
    }

}
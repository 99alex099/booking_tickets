package services.interfaces;

import dto.CreateFlightRequestDTO;
import dto.FlightDTO;
import dto.PersonDTO;
import entity.Flight;
import validators.exceptions.general.InvalidTextException;
import validators.exceptions.flights.FlightIdNotFoundException;

import java.util.List;

public interface FlightService {
    FlightDTO findFlight(Integer id);
    List<FlightDTO> findFlights() throws FlightIdNotFoundException;
    List<FlightDTO> findFlightsByCityName(FlightDTO flight) throws InvalidTextException;
    void deleteFlight(Integer id);
    FlightDTO addFlight(FlightDTO flight);
    FlightDTO addFlight(CreateFlightRequestDTO createFlightRequestDTO);
    FlightDTO updateFlight(FlightDTO flight);
}

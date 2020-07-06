package validators.exceptions.flights;

import dto.FlightDTO;

public class DepartureCityNameIsInvalidExceptionException extends FlightCityNameIsIncorrectException {
    public DepartureCityNameIsInvalidExceptionException(FlightDTO flight) {
        super(  flight,
                flight.getDepartureCity());
    }
}

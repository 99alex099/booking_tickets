package validators.exceptions.flights;

import dto.FlightDTO;

public class ArrivalCityNameIsIncorrectExceptionException extends FlightCityNameIsIncorrectException {
    public ArrivalCityNameIsIncorrectExceptionException(FlightDTO flight) {
        super(  flight,
                flight.getArrivalCity());
    }
}

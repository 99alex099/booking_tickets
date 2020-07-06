package validators.exceptions.flights;

import dto.FlightDTO;
import lombok.Getter;

public class FlightException extends RuntimeException {
    @Getter
    private FlightDTO flight;

    public FlightException(FlightDTO flight, String message) {
        super(message);
        this.flight = flight;
    }
}

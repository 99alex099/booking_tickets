package validators.exceptions.flights;

import dto.CreateFlightRequestDTO;

public class PriceException extends CreateFlightRequestException {
    public PriceException(CreateFlightRequestDTO flight) {
        super(flight, "price must be > 0");
    }
}

package validators.exceptions.flights;

import dto.CreateFlightRequestDTO;

public class DateIsIncorrectException extends CreateFlightRequestException {
    public DateIsIncorrectException(CreateFlightRequestDTO createFlightRequestDTO) {
        super(createFlightRequestDTO, "date's incorrect");
    }
}

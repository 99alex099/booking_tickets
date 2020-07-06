package validators.exceptions.flights;

import dto.CreateFlightRequestDTO;
import lombok.Getter;

public class CreateFlightRequestException extends RuntimeException {
    @Getter
    private CreateFlightRequestDTO createFlightRequestDTO;

    public CreateFlightRequestException(CreateFlightRequestDTO createFlightRequestDTO,
                                        String message) {
        super(message);
        this.createFlightRequestDTO = createFlightRequestDTO;
    }
}

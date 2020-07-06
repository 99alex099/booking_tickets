package validators.exceptions.flights;

import dto.FlightDTO;
import validators.exceptions.general.NotFoundException;

public class FlightIdNotFoundException extends FlightException {
    public FlightIdNotFoundException(Integer id) {
        super(FlightDTO.builder()
                        .id(id)
                        .build(),
                "flight[id:" + id + "] not founded");
    }
}

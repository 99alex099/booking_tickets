package validators.exceptions.planeseats;

import dto.PlaneRowDTO;

public class CountOfSeatsIsIncorrect extends PlaneSeatsException {
    public CountOfSeatsIsIncorrect(PlaneRowDTO planeRowDTO) {
        super(planeRowDTO,
                "Count of seats '" + planeRowDTO.getCountOfSeats() + "' is incorrect");
    }
}

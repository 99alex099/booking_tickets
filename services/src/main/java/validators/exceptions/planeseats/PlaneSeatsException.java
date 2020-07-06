package validators.exceptions.planeseats;

import dto.PlaneRowDTO;
import lombok.Getter;

public class PlaneSeatsException extends RuntimeException {
    @Getter
    private PlaneRowDTO planeRow;

    public PlaneSeatsException(PlaneRowDTO planeRow, String message) {
        super(message);
        this.planeRow = planeRow;
    }
}

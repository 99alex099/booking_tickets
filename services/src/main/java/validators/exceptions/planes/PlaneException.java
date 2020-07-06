package validators.exceptions.planes;

import dto.PlaneDTO;
import lombok.Getter;

public class PlaneException extends RuntimeException {
    @Getter
    private PlaneDTO plane;

    public PlaneException(PlaneDTO plane, String message) {
        super(message);
        this.plane = plane;
    }
}

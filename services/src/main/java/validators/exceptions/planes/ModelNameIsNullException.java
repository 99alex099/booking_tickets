package validators.exceptions.planes;

import dto.PlaneDTO;

public class ModelNameIsNullException extends PlaneException {
    public ModelNameIsNullException(PlaneDTO plane) {
        super(plane,
                "model's name is null");
    }
}

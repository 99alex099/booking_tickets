package validators.exceptions.planes;

import dto.PlaneDTO;

public class ModelNameIsIncorrectException extends PlaneException {
    public ModelNameIsIncorrectException(PlaneDTO plane) {
        super(plane,
                "model name '" + plane.getModelName() + "' isn't correct");
    }
}

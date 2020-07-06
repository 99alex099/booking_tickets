package validators.exceptions.planes;

import dto.PlaneDTO;
import validators.exceptions.general.InvalidTextException;

public class PlaneIdNotFoundException extends PlaneException {
    public PlaneIdNotFoundException(Integer id) {
        super(PlaneDTO.builder()
                .id(id)
                .build()
                ,"plane[" + id + "] not founded");
    }
}

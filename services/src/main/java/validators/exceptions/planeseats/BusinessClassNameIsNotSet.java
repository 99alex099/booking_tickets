package validators.exceptions.planeseats;

import dto.PlaneRowDTO;

public class BusinessClassNameIsNotSet extends PlaneSeatsException {
    public BusinessClassNameIsNotSet(PlaneRowDTO planeRow) {
        super(planeRow,
                "Value of business class isn't set");
    }
}

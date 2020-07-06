package validators.exceptions.planeseats;

import dto.PlaneRowDTO;

public class PlaneSeatIdIsIncorrect extends PlaneSeatsException {
    public PlaneSeatIdIsIncorrect(Integer id) {
        super(PlaneRowDTO.builder()
                        .id(id)
                        .build()
                , "Plane's seat[" + id + "] is not exists or incorrect");
    }
}

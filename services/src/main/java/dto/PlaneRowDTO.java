package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class PlaneRowDTO {

    private Integer id;
    private Integer countOfSeats;
    private boolean isBusinessClass;
    private PlaneDTO plane = null;
}
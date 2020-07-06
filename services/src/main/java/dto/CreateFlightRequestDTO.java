package dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateFlightRequestDTO {
    private String arrivalCity;
    private String departureCity;
    private Integer arrivalYear;
    private Integer arrivalMonth;
    private Integer arrivalDay;
    private Integer arrivalHour;
    private Integer arrivalMinute;
    private Integer departureYear;
    private Integer departureMonth;
    private Integer departureDay;
    private Integer departureHour;
    private Integer departureMinute;
    private Integer businessClassPrice;
    private Integer otherPrice;
    private Integer planeId;
}
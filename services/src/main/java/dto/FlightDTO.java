package dto;


import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDTO {

    private Integer id = 0;
    private String arrivalCity;
    private String departureCity;
    private Date arrivalDateTime;
    private Date departureDateTime;
    private PlaneDTO plane;

    @Override
    public String toString() {

        return "ID:" +
                getId() +
                "\nArrival city:" +
                getArrivalCity() +
                "\nArrival date:" +
                getArrivalDateTime() +
                "\nDeparture city:" +
                getDepartureCity() +
                "\nDeparture date:" +
                getDepartureDateTime() +
                "\nModel of plane:" +
                getPlane().getModelName();
    }
}

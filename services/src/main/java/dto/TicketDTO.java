package dto;

import entity.Flight;
import entity.Person;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class TicketDTO {

    private int id;
    private FlightDTO flight;
    private int price;
    private int row;
    private int seatNumber;
    private PersonDTO owner = null;
}

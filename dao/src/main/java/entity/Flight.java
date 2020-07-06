package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Flight implements Serializable {
    private static final long serialVersionUID = 4865903039190150223L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id = 0;
    @Column(name = "arrival_city")
    private String arrivalCity;
    @Column(name = "departure_city")
    private String departureCity;
    @Column(name = "arrival_date_time")
    private Date arrivalDateTime;
    @Column(name = "departure_date_time")
    private Date departureDateTime;
    @ManyToOne
    @JoinColumn(name = "plane_id")
    private Plane plane;

    public Flight(String arrivalCity, String departureCity, Date arrivalDateTime, Date departureDateTime, Plane plane) {
        this.plane = plane;
        this.arrivalCity = arrivalCity;
        this.departureCity = departureCity;
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
    }

    public Flight(int id, String arrivalCity, String departureCity, Date arrivalDateTime, Date departureDateTime,
                  Plane plane) {
        this.id = id;
        this.plane = plane;
        this.arrivalCity = arrivalCity;
        this.departureCity = departureCity;
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
    }

    @Override
    public String toString() {

        return "ID:" +
                getId() +
                "Arrival city:" +
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

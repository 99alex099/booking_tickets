package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "tickets")
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    @Column(name = "price")
    private int price;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_number")
    private int seatNumber;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner = null;

    public Ticket(int id, Flight flight, Person owner, int price, int row, int seatNumber) {
        this.id = id;
        this.flight = flight;
        this.price = price;
        this.row = row;
        this.seatNumber = seatNumber;
        this.owner = owner;
    }

    public Ticket(Flight flight, int price, int row, int seatNumber) {
        this.flight = flight;
        this.price = price;
        this.row = row;
        this.seatNumber = seatNumber;
    }
}

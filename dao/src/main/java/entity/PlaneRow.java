package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rows_of_plane")
public class PlaneRow { // added

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Count_of_seats")
    private int countOfSeats;
    @Column(name = "Is_business_class")
    private boolean isBusinessClass;
    @ManyToOne
    @JoinColumn(name = "plane_id")
    private Plane plane = null;
    /*@Column(name = "row_number")
    private int rowNumber;*/

}
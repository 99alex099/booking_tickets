package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Review { // added
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane reviewedPlane;
    @Column(name = "text")
    private String text;
    @Column(name = "mark")
    private Integer mark;
}

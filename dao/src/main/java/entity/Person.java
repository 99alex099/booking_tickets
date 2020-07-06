package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "persons")
public class Person { // added

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id = 0;
    @Column(name = "fullname")
    private String fullName;

    public Person(String fullName) {
        this.fullName = fullName;
    }

    public Person(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}

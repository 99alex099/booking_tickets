package dao.jpa;

import entity.Plane;
import entity.PlaneRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRowRepository extends JpaRepository<PlaneRow, Integer> {
    List<PlaneRow> findByPlane(Plane plane);
}
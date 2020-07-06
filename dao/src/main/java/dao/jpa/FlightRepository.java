package dao.jpa;

import entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findFlightsByArrivalCityAndDepartureCity(String arrivalCity,
                                                          String departureCity);
}

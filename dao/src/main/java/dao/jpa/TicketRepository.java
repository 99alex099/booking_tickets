package dao.jpa;

import entity.Person;
import entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findByOwnerAndFlightId(Person owner, int flightID);
    List<Ticket> findAllByFlight_Id(Integer id);
    List<Ticket> findAllByOwner(Person owner);
    Optional<Ticket> findByRowAndSeatNumberAndFlightId(int row,
                                                       int seatNumber,
                                                       Integer flightId);
    Optional<Ticket> findByIdAndOwner(Integer id, Person owner);
    boolean existsByFlightIdAndOwner(Integer flightId, Person owner);
}

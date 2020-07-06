package dao.jpa;

import entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByReviewedPlaneId(Integer planeId);
    @Query(value = "select * from reviews join users u on reviews.author_id = u.ID " +
            "WHERE reviews.ID = :id AND nickname = :username", nativeQuery = true)
    Optional<Review> findByIdAndAuthorName(Integer id, String username);
}

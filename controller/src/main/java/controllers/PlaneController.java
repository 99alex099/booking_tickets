package controllers;

import dto.PlaneRowDTO;
import dto.ReviewDTO;
import dto.PlaneDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ReviewService;
import services.interfaces.UserService;
import services.interfaces.PlaneService;

import java.util.List;

@RestController
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneService planeService;
    private final ReviewService reviewService;
    private final UserService userService;

    public PlaneController(PlaneService planeService,
                           ReviewService reviewService,
                           UserService userService) {
        this.planeService = planeService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("{plane_id}")
    public PlaneDTO getPlane(@PathVariable(value = "plane_id") Integer planeId) {
        return planeService.findPlaneById(planeId);
    }

    @DeleteMapping("{plane_id}")
    public void deletePlane(@PathVariable("plane_id") Integer planeId) {
        planeService.deletePlane(planeId);
    }

    @GetMapping
    public List<PlaneDTO> getPlanes() {
        return planeService.findPlanes();
    }

    @PostMapping
    public PlaneDTO addPlane(@RequestBody PlaneDTO plane) {
        plane.setId(null);
        return planeService.addPlane(plane);
    }

    @PutMapping("{plane_id}")
    public PlaneDTO setPlane(@RequestBody PlaneDTO plane,
                             @PathVariable("plane_id") Integer planeId) {
        plane.setId(planeId);
        return planeService.savePlane(plane);
    }

    @PostMapping("{plane_id}/seats")
    public PlaneRowDTO addPlaneRow(@RequestBody PlaneRowDTO planeRowDTO,
                                   @PathVariable(value = "plane_id") Integer planeId) {
        return planeService.addPlaneSeats(planeRowDTO,
                planeId);
    }
    @GetMapping("{plane_id}/seats")
    public List<PlaneRowDTO> getPlaneRows(@PathVariable("plane_id") Integer planeId) {
        return planeService.findPlaneSeats(planeId);
    }
    @DeleteMapping("{plane_id}/seats/{deleted_seat_id}")
    public void deletePlaneRow(@PathVariable("deleted_seat_id") Integer deletedSeatId) {
       planeService.deletePlaneSeat(deletedSeatId);
    }
    @GetMapping("{plane_id}/reviews")
    public List<ReviewDTO> getReviews(@PathVariable("plane_id") Integer planeId) {
        return reviewService.findReviews(planeId);
    }
    @PostMapping("{plane_id}/reviews")
    public ReviewDTO addReview(@RequestBody ReviewDTO review,
                               @PathVariable("plane_id") Integer planeId) {
        String name = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        review.setReviewedPlane(
                planeService.findPlaneById(planeId)
        );

        review.setAuthor(
                userService.findUserByUsername(name)
        );
        return reviewService.addReview(review);
    }
}

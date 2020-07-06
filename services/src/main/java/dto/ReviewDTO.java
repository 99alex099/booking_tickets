package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Integer id;
    private UserDTO author;
    private PlaneDTO reviewedPlane;
    private String text;
    private Integer mark;
}

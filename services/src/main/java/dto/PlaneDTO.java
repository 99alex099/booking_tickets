package dto;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaneDTO {
    private Integer id = 0;
    private String modelName;
}
package dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Integer id;
    private String fullName;
}

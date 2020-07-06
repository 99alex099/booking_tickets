package dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO<DTO> {
    private String message;
    private HttpStatus status;
    private DTO errorDTO;
}

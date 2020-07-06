package dto;

import entity.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private Integer id = 0;
    private String username;
    private Role role = Role.ROLE_USER;
    private PersonDTO person;

    @Override
    public String toString() {
        return "id:" + getId() + " nickname:" + getUsername() +
                "  p.id:" + person.getId() + " full name:" + person.getFullName();
    }
}

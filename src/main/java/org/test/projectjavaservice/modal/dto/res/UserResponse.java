package org.test.projectjavaservice.modal.dto.res;

import lombok.*;
import org.test.projectjavaservice.modal.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Role role;

    private Boolean isEnabled;
}

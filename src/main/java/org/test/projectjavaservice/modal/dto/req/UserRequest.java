package org.test.projectjavaservice.modal.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.projectjavaservice.modal.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Role role;

    private Boolean isEnabled;
}

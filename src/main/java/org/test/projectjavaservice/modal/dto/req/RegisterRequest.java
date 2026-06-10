package org.test.projectjavaservice.modal.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.projectjavaservice.modal.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username can not blank")
    @Size(min = 4, max = 20, message = "username must be between 4 and 20 characters long")
    private String username;

    @NotBlank(message = "password can not blank")
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "fullname can not blank")
    private String fullName;

    @NotBlank(message = "email can not blank")
    @Email(message = "email is invalid")
    private String email;
    @NotBlank(message = "phone number can not blank")
    private String phoneNumber;
    private Role role;
    private Boolean isEnabled;
}

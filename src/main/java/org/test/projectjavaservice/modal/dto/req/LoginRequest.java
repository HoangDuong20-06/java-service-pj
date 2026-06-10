package org.test.projectjavaservice.modal.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username can not blank")
    private String username;
    @NotBlank(message = "Password can not blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}

package org.test.projectjavaservice.modal.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "Username can not be blank")
    private String username;
    @NotNull(message = "Full name can not be blank")
    private String fullName;
    @NotNull(message = "Email can not be blank")
    private String email;
    @NotNull(message = "Phone number can not be blank")
    private String phoneNumber;
    private Boolean isEnabled;
}

package org.test.projectjavaservice.modal.dto.res;

import lombok.*;
import org.test.projectjavaservice.modal.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private String username;
    private Role role;
}

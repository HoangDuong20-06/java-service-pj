package org.test.projectjavaservice.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    private String phoneNumber;
    private Boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user")
    private List<TokenBlacklist> blacklists;

    @OneToMany(mappedBy = "manager")
    private List<BadmintonCluster> clusters;
}

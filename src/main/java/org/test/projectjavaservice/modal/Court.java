package org.test.projectjavaservice.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courtName;
    private String imageUrl;
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private BadmintonCluster cluster;

    @OneToMany(mappedBy = "court")
    private List<Booking> bookings;
}

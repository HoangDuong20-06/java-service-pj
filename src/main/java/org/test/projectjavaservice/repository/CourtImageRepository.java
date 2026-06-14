package org.test.projectjavaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.projectjavaservice.modal.CourtImage;

public interface CourtImageRepository extends JpaRepository<CourtImage, Long> {
    CourtImage save(CourtImage courtImage);
}

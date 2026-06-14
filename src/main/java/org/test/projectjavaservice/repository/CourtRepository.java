package org.test.projectjavaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.projectjavaservice.modal.Court;
import org.test.projectjavaservice.modal.CourtImage;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    CourtImage save(CourtImage courtImage);
}

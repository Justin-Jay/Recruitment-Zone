package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.ContactPerson;

import java.util.List;
import java.util.Optional;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {

    Optional<ContactPerson> findContactPersonByClientID(Long id);
    List<ContactPerson> findContactPeopleByClientID(Long id);
}

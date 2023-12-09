package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.entity.domain.CandidateNote;
import za.co.RecruitmentZone.entity.domain.Client;
import za.co.RecruitmentZone.entity.domain.ClientNote;
import za.co.RecruitmentZone.entity.domain.ContactPerson;

import java.util.List;
import java.util.Optional;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {


    List<ContactPerson> findContactPersonByClient_ClientID(Long clientID);
}

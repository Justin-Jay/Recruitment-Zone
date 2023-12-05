package za.co.RecruitmentZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.Client;
import za.co.RecruitmentZone.entity.domain.ClientNote;
import za.co.RecruitmentZone.entity.domain.ContactPerson;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

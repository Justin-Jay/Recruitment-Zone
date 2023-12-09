package za.co.RecruitmentZone.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.RecruitmentZone.client.entity.ContactPerson;

import java.util.List;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {


    List<ContactPerson> findContactPersonByClient_ClientID(Long clientID);
}

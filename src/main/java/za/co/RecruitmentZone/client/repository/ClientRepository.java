package za.co.RecruitmentZone.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.RecruitmentZone.client.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

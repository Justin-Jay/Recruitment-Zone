package za.co.recruitmentzone.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.client.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

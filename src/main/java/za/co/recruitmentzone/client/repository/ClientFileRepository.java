package za.co.recruitmentzone.client.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.client.entity.ClientFile;

import java.util.List;

@Repository
public interface ClientFileRepository extends JpaRepository<ClientFile, Long> {

    @Query("SELECT d FROM ClientFile d WHERE d.client.clientID = :clientID")
    List<ClientFile> findClientFileById(Long clientID);


}
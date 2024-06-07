package za.co.recruitmentzone.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientNote;

@Repository
public interface ClientNoteRepository extends JpaRepository<ClientNote, Long> {

    @Query("SELECT cn FROM ClientNote cn WHERE cn.client = :client")
    Page<ClientNote> findAllByClient(Pageable pageable, @Param("client") Client client);


}

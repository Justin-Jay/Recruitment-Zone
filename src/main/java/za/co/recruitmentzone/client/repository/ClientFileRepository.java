package za.co.recruitmentzone.client.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.List;

@Repository
public interface ClientFileRepository extends JpaRepository<ClientFile, Long> {

    @Query("SELECT d FROM ClientFile d WHERE d.client.clientID = :clientID")
    List<ClientFile> findClientFileById(Long clientID);


    @Query("SELECT d FROM ClientFile d WHERE d.vacancy.vacancyID = :vacancyID")
    List<ClientFile> findClientFileByVacancyID(Long vacancyID);

    @Query("SELECT cf FROM ClientFile cf WHERE cf.vacancy = :vacancy")
    Page<ClientFile> findAllByVacancy(Pageable pageable, @Param("vacancy") Vacancy vacancy);


}
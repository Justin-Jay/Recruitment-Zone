package za.co.recruitmentzone.client.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.candidate.entity.CandidateNote;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.List;

@Repository
public interface CandidateFileRepository extends JpaRepository<CandidateFile, Long> {

    @Query("SELECT d FROM CandidateFile d WHERE d.candidate.candidateID = :candidateId")
    List<CandidateFile> findByCandidateID(Long candidateId);


    @Query("SELECT cf FROM CandidateFile cf WHERE cf.candidate = :candidate")
    Page<CandidateFile> findAllByCandidate(Pageable pageable, @Param("candidate") Candidate candidate);


}
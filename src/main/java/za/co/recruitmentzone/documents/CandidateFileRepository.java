package za.co.recruitmentzone.documents;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateFileRepository extends JpaRepository<CandidateFile, Long> {

    @Query("SELECT d FROM CandidateFile d WHERE d.candidate.candidateID = :candidateId")
    List<CandidateFile> findByCandidateID(Long candidateId);
}
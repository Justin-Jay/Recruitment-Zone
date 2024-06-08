package za.co.recruitmentzone.candidate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.entity.CandidateNote;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.entity.ClientNote;

@Repository
public interface CandidateNoteRepository extends JpaRepository<CandidateNote, Long> {

    @Query("SELECT cn FROM CandidateNote cn WHERE cn.candidate = :candidate")
    Page<CandidateNote> findAllByCandidate(Pageable pageable, @Param("candidate") Candidate candidate);


}

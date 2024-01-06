package za.co.recruitmentzone.documents;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateFileRepository extends JpaRepository<CandidateFile, Long> {

}
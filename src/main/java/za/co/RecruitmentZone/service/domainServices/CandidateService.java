package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.CandidateNote;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.repository.CandidateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }
    public Candidate save(Candidate candidate){
        return candidateRepository.save(candidate);
    }

public Candidate getcandidateByID(Long clientID){
        Optional<Candidate> oc = candidateRepository.findById(clientID);
        Candidate c = null;
        if (oc.isPresent()){
            c= oc.get();
        }
        return c;

}


}

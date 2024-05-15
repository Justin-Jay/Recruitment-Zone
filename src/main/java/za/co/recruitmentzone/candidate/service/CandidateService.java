package za.co.recruitmentzone.candidate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.exception.CandidateNotFoundException;
import za.co.recruitmentzone.candidate.repository.CandidateRepository;

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

    public Candidate save(Candidate candidate) {
        log.info("About to Save candidate \n {}", candidate.printCandidate());
        return candidateRepository.save(candidate);
    }

    public Candidate getcandidateByID(Long clientID) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(clientID);
        return optionalCandidate.orElseThrow(() -> new CandidateNotFoundException("Could not find candidate with ID " + clientID));
    }


}

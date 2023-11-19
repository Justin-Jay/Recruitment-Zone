package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.CandidateApplication;
import za.co.RecruitmentZone.repository.CandidateApplicationRepository;
@Service
public class CandidateApplicationService {
    private final Logger log = LoggerFactory.getLogger(CandidateApplicationService.class);

    private final CandidateApplicationRepository candidateApplicationRepository;

    public CandidateApplicationService(CandidateApplicationRepository candidateApplicationRepository) {
        this.candidateApplicationRepository = candidateApplicationRepository;
    }

    public CandidateApplication save(CandidateApplication candidateApplication){
        return candidateApplicationRepository.save(candidateApplication);
    }
}

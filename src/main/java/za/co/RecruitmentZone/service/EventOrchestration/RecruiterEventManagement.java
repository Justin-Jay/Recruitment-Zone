package za.co.RecruitmentZone.service.EventOrchestration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Recruiter;
import za.co.RecruitmentZone.repository.RecruiterRepository;

import java.util.List;

@Service
public class RecruiterEventManagement {

    private final RecruiterRepository recruiterRepository;

    @Autowired
    public RecruiterEventManagement(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public void addRecruiter(Recruiter recruiter) {
        // Perform any necessary validation or business logic
        recruiterRepository.save(recruiter);
    }

    public List<Recruiter> getAllRecruiters() {
        return (List<Recruiter>) recruiterRepository.findAll();
    }

    // Add more methods for recruiter-related operations
}


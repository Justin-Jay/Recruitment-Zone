package za.co.RecruitmentZone.Recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
class RecruiterService {

    private final RecruiterRepository recruiterRepository;

    @Autowired
    public RecruiterService(RecruiterRepository recruiterRepository) {
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


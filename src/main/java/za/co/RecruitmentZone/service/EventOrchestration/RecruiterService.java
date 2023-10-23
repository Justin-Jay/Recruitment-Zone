package za.co.RecruitmentZone.service.EventOrchestration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.ApplicationUser;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;
import java.util.List;

@Service
public class RecruiterService {

    private final ApplicationUserRepository recruiterRepository;

    @Autowired
    public RecruiterService(ApplicationUserRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    public void addRecruiter(ApplicationUser recruiter) {
        // Perform any necessary validation or business logic
        recruiterRepository.save(recruiter);
    }

    public List<ApplicationUser> getAllRecruiters() {
        return (List<ApplicationUser>) recruiterRepository.findAll();
    }

    // Add more methods for recruiter-related operations


}


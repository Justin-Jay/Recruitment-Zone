package za.co.RecruitmentZone.RecruitmentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
class RecruitmentManagerService {

    private final RecruitmentManagerRepository recruitmentManagerRepository;

    @Autowired
    public RecruitmentManagerService(RecruitmentManagerRepository recruitmentManagerRepository) {
        this.recruitmentManagerRepository = recruitmentManagerRepository;
    }

    public void addRecruitmentManager(RecruitmentManager manager) {
        // Perform any necessary validation or business logic
        recruitmentManagerRepository.save(manager);
    }

    public List<RecruitmentManager> getAllRecruitmentManagers() {
        return (List<RecruitmentManager>) recruitmentManagerRepository.findAll();
    }

    // Add more methods for recruitment manager-related operations
}


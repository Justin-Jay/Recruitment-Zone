package za.co.RecruitmentZone.documents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    private final Logger log = LoggerFactory.getLogger(FileService.class);
    private final FileRepository fileRepository;
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public CandidateFile save(CandidateFile candidateFile){
        return fileRepository.save(candidateFile);
    }
}

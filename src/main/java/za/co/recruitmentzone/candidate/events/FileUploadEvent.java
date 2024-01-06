package za.co.recruitmentzone.candidate.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadEvent extends ApplicationEvent {
    private Long vacancyID;

    private Long candidateID;
    MultipartFile multipartFile;

    public FileUploadEvent(MultipartFile multipartFile,Long vacancyID,Long candidateID) {
        super(multipartFile);
        this.candidateID=candidateID;
        this.vacancyID=vacancyID;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}


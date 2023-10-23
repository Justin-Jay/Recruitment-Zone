package za.co.RecruitmentZone.events.Candidate;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.ApplicationUser;

import java.time.Clock;

public class FileUploadEvent extends ApplicationEvent {
    private String fileName;

    MultipartFile multipartFile;

    public FileUploadEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public FileUploadEvent(Object source, String fileName) {
        super(source);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}


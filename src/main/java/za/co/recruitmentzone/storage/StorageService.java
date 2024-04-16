package za.co.recruitmentzone.storage;

import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.service.CandidateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class StorageService {
    private final Storage storage;
    private final CandidateService candidateService;
    private final Logger log = LoggerFactory.getLogger(StorageService.class);
    public StorageService(Storage storage, CandidateService candidateService) {
        this.storage = storage;
        this.candidateService = candidateService;
    }

    public String uploadFile(CandidateFileDTO fileDTO) {
        String filename = "";
        try {
            Optional<Candidate> candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
            if (candidate.isPresent()) {
                Candidate c = candidate.get();
                String docType = fileDTO.getDocumentType().toString();
                String candidateIDNumber = c.getId_number();
                String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
                String fileName = fileDTO.getCvFile().getName();
                String filePathSplit = "/";
                String destinationFileName = "CANDIDATE_FILES" + filePathSplit + candidateIDNumber + filePathSplit + docType + filePathSplit + formattedDate + fileName;

                BlobId id = BlobId.of("kiunga", destinationFileName);
                BlobInfo info = BlobInfo.newBuilder(id).build();

                Blob uploadedFile = storage.create(info, fileDTO.getCvFile().getBytes());
                filename = uploadedFile.getName();
            }
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
        return "File uploaded Success: " + filename;
    }

    public String testMethod() throws IOException {

        log.info("Test method");
        String destinationFileName = "newFolder" + "/" + "New File Name.txt";
        BlobId id = BlobId.of("kiunga", destinationFileName);
        BlobInfo info = BlobInfo.newBuilder(id).build();
// "C:\demo.txt"
        File file = new File("C:/", "demo.txt");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        Blob saved = storage.create(info, arr);

        log.info("saved result {}", saved.getName());
        return "File Uploaded Successfully" + saved.getName();
    }

}

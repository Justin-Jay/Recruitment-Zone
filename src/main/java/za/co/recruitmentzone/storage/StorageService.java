package za.co.recruitmentzone.storage;

import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.service.CandidateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class StorageService {


    private final Storage storage;
    private final CandidateService candidateService;

    public StorageService(Storage storage, CandidateService candidateService) {
        this.storage = storage;
        this.candidateService = candidateService;
    }

    public String uploadFile(CandidateFileDTO fileDTO) {
         Candidate candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
        try {
            String docType = fileDTO.getDocumentType().toString();
            String candidateIDNumber = candidate.getId_number();
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
            String fileName = fileDTO.getCvFile().getName();
            String filePathSplit = "/";
            String destinationFileName = "CANDIDATE_FILES"+ filePathSplit +candidateIDNumber+ filePathSplit +docType+ filePathSplit +formattedDate+fileName;

            BlobId id = BlobId.of("kiunga", destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            Blob uploadedFile = storage.create(info, fileDTO.getCvFile().getBytes());

            return "File uploaded: " + uploadedFile.getName();
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    public String testMethod() throws IOException {

        System.out.println("Test method");
        String destinationFileName = "newFolder" + "/" + "New File Name.txt";
        BlobId id = BlobId.of("kiunga", destinationFileName);
        BlobInfo info = BlobInfo.newBuilder(id).build();
// "C:\demo.txt"
        File file = new File("C:/","demo.txt");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        Blob saved = storage.create(info,arr);

        System.out.println("saved result "+saved.getName());
        return "File Uploaded Successfully"+saved.getName();
    }

}

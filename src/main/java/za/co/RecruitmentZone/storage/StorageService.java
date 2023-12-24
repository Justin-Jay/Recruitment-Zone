package za.co.RecruitmentZone.storage;

import com.google.cloud.storage.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.candidate.dto.CandidateFileDTO;
import za.co.RecruitmentZone.candidate.entity.Candidate;
import za.co.RecruitmentZone.candidate.service.CandidateService;
import za.co.RecruitmentZone.documents.CandidateFile;

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

/*
    public String uploadFile(Long vacancyID,MultipartFile multi) {
        try {

            if (multi.getSize() > 25 * 1024 * 1024) {
                return "File size exceeds the limit (25MB)";
            }

            if (!isValidContentType(multi)) {
                return "Invalid file format. Only PDF and Word documents are allowed.";
            }


            // Perform the actual file upload to storage
            String destinationFileName = "CurriculumVitae" + "/" +"Submissions"+vacancyID+"/"+ multi.getName();
            BlobId id = BlobId.of("kiunga", destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            Blob uploadedFile = storage.create(info, multi.getBytes());

            return "File uploaded: " + uploadedFile.getName();
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }
*/

    public String uploadFile(CandidateFileDTO fileDTO) {
         Candidate candidate = candidateService.getcandidateByID(fileDTO.getCandidateID());
        try {
            String docType = fileDTO.getDocumentType().toString();
            String candidateIDNumber = candidate.getId_number();
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
            String fileName = fileDTO.getCvFile().getName();
            String destinationFileName = "CANDIDATE_FILES/"+candidateIDNumber+"/"+docType+"/"+formattedDate+fileName;

            BlobId id = BlobId.of("kiunga", destinationFileName);
            BlobInfo info = BlobInfo.newBuilder(id).build();

            Blob uploadedFile = storage.create(info, fileDTO.getCvFile().getBytes());

            return "File uploaded: " + uploadedFile.getName();
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    private boolean isValidContentType(MultipartFile file) {
        try {
            String detectedContentType = new Tika().detect(file.getInputStream());
            return detectedContentType.equals("application/pdf") || detectedContentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IOException e) {
            //log.info(e.getMessage());
            return false;
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

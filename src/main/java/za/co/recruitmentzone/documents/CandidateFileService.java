package za.co.recruitmentzone.documents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateFileService {
    private final Logger log = LoggerFactory.getLogger(CandidateFileService.class);
    private final CandidateFileRepository fileRepository;

    public CandidateFileService(CandidateFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public CandidateFile save(CandidateFile candidateFile) {
        return fileRepository.save(candidateFile);
    }

    public String getDocumentLocation(String id){
        Long fileID = Long.valueOf(id);
        String docLocation = "";
        Optional<CandidateFile> optionalCandidateFile = fileRepository.findById(fileID);
        if(optionalCandidateFile.isPresent()){
            CandidateFile file = optionalCandidateFile.get();
            docLocation = file.getDocumentLocation();
        }
        return docLocation;
    }

    public List<String> searchFiles(String searchTerm) {
        List<String> matchingFiles = new ArrayList<>();
        log.info("searchTerm {}", searchTerm);

        // Implement your file search logic here
        // For simplicity, let's assume you are searching for files by name
        String directoryPath = "C:/uploads/";

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        log.info("searchTerm {}", searchTerm);
        List<String> temp = new ArrayList<>();
        if (files != null) {
            log.info("files.length {}", files.length);
            for (File file : files) {
                log.info("file Names {}", file.getName());
                if (file.isFile()) {
                    temp.add(file.getName());
                }
            }
        } else {
            log.info("files.length is empty {}", files.length);
        }

        matchingFiles = temp.stream()
                .filter(fileName -> containsIgnoreCase(fileName, searchTerm))
                .collect(Collectors.toList());


        return matchingFiles;
    }


    public List<String> searchFileContent(String searchTerm) {

        // USER LUCENE

        return null;
    }

    private boolean containsIgnoreCase(String source, String searchTerm) {
        return source.toLowerCase().contains(searchTerm.toLowerCase());
    }

}

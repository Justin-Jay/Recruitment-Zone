package za.co.recruitmentzone.documents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface DocumentService {

    final Logger log = LoggerFactory.getLogger(DocumentService.class);

    default List<String> searchFiles(String searchTerm) {
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

    default List<String> searchFileContent(String searchTerm) {

        // USER LUCENE

        return null;
    };

    Document findFileById(Long id);
    List<?> getFiles(Long id);
    String getDocumentLocation(String id);

    default boolean containsIgnoreCase(String source, String searchTerm) {
        return source.toLowerCase().contains(searchTerm.toLowerCase());
    }
}

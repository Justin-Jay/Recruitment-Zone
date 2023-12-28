package za.co.RecruitmentZone.documents;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/Document")
public class DocumentController {

    private final CandidateFileService fileService;
    private final Logger log = LoggerFactory.getLogger(DocumentController.class);

    public DocumentController(CandidateFileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/document-administration")
    public String documentAdmin(Model model) {
        model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
        return "fragments/documents/document-administration";
    }


    @PostMapping("/searchDocuments")
    public String searchDocuments(@Valid
                                  @ModelAttribute("searchDocumentsDTO")
                                  SearchDocumentsDTO searchDocumentsDTO,
                                  BindingResult bindingResult, Model model) {
        log.info("searchDocuments TRIGGERED");
        log.info("searchDocumentsDTO {}",searchDocumentsDTO);
        if (bindingResult.hasFieldErrors()) {
            log.info("searchDocuments HAS ERRORS");
            log.info("searchDocumentsDTO {}",searchDocumentsDTO);
            // If there are validation errors, handle them accordingly
            // You might want to return to the form page with error messages
            model.addAttribute("message", "Please try again");
            return "fragments/documents/document-administration"; // Replace with your error handling logic
        }
        log.info("Search DTO: " + searchDocumentsDTO);
        String term = searchDocumentsDTO.getTerm();
        log.info("Search term: " + term);
        List<String> filesNames = fileService.searchFiles(term);
        log.info("Search Results: " + filesNames.size());
        model.addAttribute("resultCount", filesNames.size());
        model.addAttribute("results", filesNames);
        model.addAttribute("term", term);
        return "fragments/documents/document-administration";
    }

    @PostMapping("/searchFileContents")
    public String searchFileContents(@Valid
                                  @ModelAttribute("searchDocumentsDTO")
                                  SearchDocumentsDTO searchDocumentsDTO,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            log.info("searchDocuments HAS ERRORS");
            log.info("searchDocumentsDTO {}",searchDocumentsDTO);
            // If there are validation errors, handle them accordingly
            // You might want to return to the form page with error messages
            model.addAttribute("message", "Please try again");
            return "fragments/documents/document-administration"; // Replace with your error handling logic
        }
        log.info("Search DTO: " + searchDocumentsDTO);
        String term = searchDocumentsDTO.getTerm();
        log.info("Search term: " + term);
        List<String> filesNames = fileService.searchFileContent(term);
        log.info("Search Results: " + filesNames.size());
        model.addAttribute("resultCount", filesNames.size());
        model.addAttribute("results", filesNames);
        model.addAttribute("term", term);
        return "fragments/documents/document-administration";
    }

    //   String directory = "C:/uploads/"+formattedDate+"/"+docType+"/"+candidateIDNumber;
    @GetMapping("/download-document/{id}")
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable String id) throws IOException {
        // Specify the path to the file on your local machine
        id = "_1.pdf";
        String filePath = "C:\\uploads\\Spring_details"+ id; // Update with the actual path


        // Read the file from the local machine
        InputStream inputStream = new FileInputStream(filePath);

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", id); // Use the file name as attachment name

        // Create an InputStreamResource from the file's content
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Return the ResponseEntity with the InputStreamResource and headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}

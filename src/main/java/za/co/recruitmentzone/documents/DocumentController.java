package za.co.recruitmentzone.documents;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
import za.co.recruitmentzone.candidate.dto.CandidateFileDTO;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.client.dto.ClientFileDTO;
import za.co.recruitmentzone.client.exception.FileContentException;
import za.co.recruitmentzone.controller.RecruitmentZoneAPIController;
import za.co.recruitmentzone.exception.DocumentLocationError;
import za.co.recruitmentzone.exception.NoResultsFoundException;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Document")
public class DocumentController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(DocumentController.class);

    public DocumentController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // internalServerError , searchDocumentsDTO
    @GetMapping("/document-administration")
    public String documentAdmin(Model model) {
        try {
            model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
        } catch (Exception e) {
            log.info(e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
     // searchDocumentsDTO , internalServerError
        return "fragments/documents/document-administration";
    }


    @PostMapping("/searchDocuments")
    public String searchDocuments(@Valid
                                  @ModelAttribute("searchDocumentsDTO")
                                  SearchDocumentsDTO searchDocumentsDTO,
                                  BindingResult bindingResult, Model model) {
        log.info("searchDocumentsDTO {}", searchDocumentsDTO);
        if (bindingResult.hasFieldErrors()) {
            log.info("searchDocumentsDTO Term {}", searchDocumentsDTO.getTerm());
            model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
            model.addAttribute("bindingResult", INTERNAL_SERVER_ERROR);
            return "fragments/documents/document-administration";
        }
        try {
            log.info("Search DTO: {}", searchDocumentsDTO);
            recruitmentZoneService.searchFiles(model, searchDocumentsDTO);
            model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
        } catch (Exception e) {
            log.info(e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // resultCount , resultList , term , searchFilesResponse , internalServerError

        return "fragments/documents/document-administration";
    }

    // internalServerError ,resultCount , results, term, searchFileContentResponse
    @PostMapping("/searchFileContents")
    public String searchFileContents(@Valid
                                     @ModelAttribute("searchDocumentsDTO")
                                     SearchDocumentsDTO searchDocumentsDTO,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {

            log.info("searchDocumentsDTO {}", searchDocumentsDTO);
            model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
            model.addAttribute("bindingResult",INTERNAL_SERVER_ERROR);
            return "fragments/documents/document-administration";
        }
        try {
            log.info("Search DTO: {}", searchDocumentsDTO.getTerm());
            recruitmentZoneService.getFilesFromContent(searchDocumentsDTO, model);
            model.addAttribute("searchDocumentsDTO", new SearchDocumentsDTO());
        } catch (Exception e) {
            log.info(e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // resultCount , resultList , term , searchFilesResponse , internalServerError
        return "fragments/documents/document-administration";
    }

    @GetMapping("/download-document/{id}")
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable String id) throws IOException {
        log.info("<-- downloadDocument --> id: \n {}", id);
        // Specify the path to the file on your local machine
        // id = "_1.pdf";
        //String filePath = "C:\\uploads\\Spring_details"+ id; // Update with the actual path

// /home/justin/RecruitmentZoneApplication/FileUploads/CANDIDATE_FILES/158568598712/CURRICULUM_VITAE/2024_01_31/sample.pdf
        // Read the file from the local machine
        String filePath = recruitmentZoneService.getDocumentLocation(id);
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

package za.co.RecruitmentZone.documents;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.candidate.dto.CandidateFileDTO;

import java.util.List;

@Controller
public class DocumentController {

    private final FileService fileService;
    private final Logger log = LoggerFactory.getLogger(DocumentController.class);

    public DocumentController(FileService fileService) {
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


}

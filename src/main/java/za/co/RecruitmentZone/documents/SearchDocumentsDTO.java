package za.co.RecruitmentZone.documents;

import jakarta.validation.constraints.NotEmpty;


public class SearchDocumentsDTO {
    @NotEmpty(message = "search value cannot be empty")
    private String term;

    // Getters and setters

    public SearchDocumentsDTO() {
    }

    public SearchDocumentsDTO(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}


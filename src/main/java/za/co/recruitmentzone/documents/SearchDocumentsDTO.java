package za.co.recruitmentzone.documents;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;


public class SearchDocumentsDTO implements Serializable {
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


package za.co.RecruitmentZone.entity.domain;
public class VacancySubmission {
    // everything we need from the vacancy submission form

    private String first_name;

    private Long applicationID;

    public VacancySubmission() {
    }

    public VacancySubmission(String first_name) {
        this.first_name = first_name;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}

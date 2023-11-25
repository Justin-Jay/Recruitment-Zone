package za.co.RecruitmentZone.entity.domain;
public class VacancySubmission {
    // everything we need from the vacancy submission form
    // takes in a form which has everything required to create a candidate and an application for a vacancy
    private Candidate candidate;
    private String first_name;
    private Long applicationID;

    public VacancySubmission() {
    }

    public VacancySubmission(String first_name, Long applicationID) {
        this.first_name = first_name;
        this.applicationID = applicationID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    @Override
    public String toString() {
        return "VacancySubmission{" +
                "first_name='" + first_name + '\'' +
                ", applicationID=" + applicationID +
                '}';
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}

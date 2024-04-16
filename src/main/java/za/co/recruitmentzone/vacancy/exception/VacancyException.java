package za.co.recruitmentzone.vacancy.exception;

public class VacancyException extends RuntimeException {
    private String failureReason;

    public VacancyException(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}

package za.co.RecruitmentZone.employee.exception;

public class CreateEmployeeException extends Exception {
    String failureReason;

    public CreateEmployeeException(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}

package za.co.RecruitmentZone.employee.exception;

public class UserAlreadyExistsException extends Exception {
    String failureReason;

    public UserAlreadyExistsException(String failureReason) {
        this.failureReason = failureReason;
    }


    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}

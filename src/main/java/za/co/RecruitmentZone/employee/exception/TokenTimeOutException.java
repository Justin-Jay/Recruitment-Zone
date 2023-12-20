package za.co.RecruitmentZone.employee.exception;

public class TokenTimeOutException extends Exception {
    String failureReason;

    public TokenTimeOutException(String failureReason) {
        this.failureReason = failureReason;
    }


    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}

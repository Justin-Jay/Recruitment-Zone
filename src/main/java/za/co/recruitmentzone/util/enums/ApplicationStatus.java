package za.co.recruitmentzone.util.enums;

public enum ApplicationStatus {
   PENDING,
    REJECTED,
    SHORT_LISTED,
    INTERVIEW,
    OFFER;

    public String getPrintableStatus() {
        switch (this) {
            case OFFER:
                return "OFFER";
            case PENDING:
                return "PENDING";
            case SHORT_LISTED:
                return "SHORT LISTED";
            case INTERVIEW:
                return "INTERVIEW";
            case REJECTED:
                return "REJECTED";
            default:
                return "UNKNOWN";
        }
    }
}

package za.co.recruitmentzone.util.enums;

public enum ClientDocumentType {
    ROLE_PROFILE;

    public String getPrintableDocType() {
        switch (this) {
            case ROLE_PROFILE:
                return "ROLE PROFILE";
            default:
                return "OTHER";
        }
    }
}

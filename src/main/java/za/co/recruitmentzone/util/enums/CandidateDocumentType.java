package za.co.recruitmentzone.util.enums;

public enum CandidateDocumentType {
    CURRICULUM_VITAE,
    QUALIFICATION_CERTIFICATE,
    ID_DOCUMENT,
    DRIVERS_LICENCE,
    PAYSLIP;

    public String getPrintableDocType() {
        switch (this) {
            case CURRICULUM_VITAE:
                return "CURRICULUM VITAE";
            case QUALIFICATION_CERTIFICATE:
                return "QUALIFICATION CERTIFICATE";
            case ID_DOCUMENT:
                return "ID DOCUMENT";
            case DRIVERS_LICENCE:
                return "DRIVERS LICENCE";
            case PAYSLIP:
                return "PAY SLIP";
            default:
                return "OTHER";
        }
    }
}

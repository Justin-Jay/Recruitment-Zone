package za.co.recruitmentzone.util.enums;

public enum EmpType {
    FULLTIME,
    PARTIME;

    public String getPrintableEmpType() {
        switch (this) {
            case FULLTIME:
                return "FULL TIME";
            case PARTIME:
                return "PART TIME";
            default:
                return "OTHER";
        }
    }
}

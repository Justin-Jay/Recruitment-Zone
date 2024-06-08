package za.co.recruitmentzone.util.enums;

public enum EducationLevel {
    MATRIC_CERTIFICATE,
    BACHELORS_DEGREE,
    NATIONAL_CERTIFICATE,
    HONORS,
    NQF_LEVEL_5,
    NQF_LEVEL_6;

    public String getPrintableEduLevel() {
        switch (this) {
            case MATRIC_CERTIFICATE:
                return "MATRIC CERTIFICATE";
            case BACHELORS_DEGREE:
                return "BACHELORS DEGREE";
            case NATIONAL_CERTIFICATE:
                return "NATIONAL CERTIFICATE";
            case HONORS:
                return "HONORS";
            case NQF_LEVEL_5:
                return "NQF LEVEL 5";
            case NQF_LEVEL_6:
                return "NQF LEVEL 6";
            default:
                return "OTHER";
        }
    }

}

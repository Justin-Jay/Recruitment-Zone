package za.co.recruitmentzone.util.enums;

public enum Industry {
    AGRICULTURE,
    CONSTRUCTION,
    ELECTRICAL,
    FINANCE,
    FMCG,
    HEALTHCARE,
    INFORMATION_TECHNOLOGY,
    INSURANCE,
    LOGISTICS,
    MINING,
    TRANSPORT;

    public String getPrintableIndustryName() {
        switch (this) {
            case AGRICULTURE:
                return "AGRICULTURE";
            case CONSTRUCTION:
                return "CONSTRUCTION";
            case ELECTRICAL:
                return "ELECTRICAL";
            case FINANCE:
                return "FINANCE";
            case FMCG:
                return "FMCG";
            case HEALTHCARE:
                return "HEALTHCARE";
            case INFORMATION_TECHNOLOGY:
                return "INFORMATION TECHNOLOGY";
            case INSURANCE:
                return "INSURANCE";
            case LOGISTICS:
                return "LOGISTICS";
            case MINING:
                return "MINING";
            case TRANSPORT:
                return "TRANSPORT";
            default:
                return "OTHER";
        }
    }


}


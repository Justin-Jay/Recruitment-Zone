package za.co.recruitmentzone.util.enums;

public enum Province {
    GAUTENG,
    LIMPOPO,
    MPUMALANGA,
    KWAZULU_NATAL,
    WESTERN_CAPE,
    NORTHERN_CAPE,
    FREE_STATE,
    EASTERN_CAPE,
    NORTH_WEST;

    public String getPrintableProvinceName() {
        switch (this) {
            case GAUTENG:
                return "GAUTENG";
            case LIMPOPO:
                return "LIMPOPO";
            case MPUMALANGA:
                return "MPUMALANGA";
            case KWAZULU_NATAL:
                return "KWAZULU NATAL";
            case WESTERN_CAPE:
                return "WESTERN CAPE";
            case NORTHERN_CAPE:
                return "NORTHERN CAPE";
            case FREE_STATE:
                return "FREE STATE";
            case EASTERN_CAPE:
                return "EASTERN CAPE";
            case NORTH_WEST:
                return "NORTH WEST";
            default:
                return "OTHER";
        }
    }
}


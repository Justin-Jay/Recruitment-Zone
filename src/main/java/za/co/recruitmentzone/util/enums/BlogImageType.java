package za.co.recruitmentzone.util.enums;

public enum BlogImageType {
    HEADER,
    CONTENT_IMAGE_ONE;

    public String getPrintableImageType() {
        return switch (this) {
            case HEADER -> "HEADER";
            case CONTENT_IMAGE_ONE -> "CONTENT IMAGE ONE";
        };
    }
}

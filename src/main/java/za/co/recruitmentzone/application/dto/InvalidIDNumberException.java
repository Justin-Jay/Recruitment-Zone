package za.co.recruitmentzone.application.dto;

class  InvalidIDNumberException extends RuntimeException {
    public InvalidIDNumberException(String message) {
        super(message);
    }
}

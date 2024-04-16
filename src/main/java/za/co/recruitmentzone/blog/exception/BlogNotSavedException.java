package za.co.recruitmentzone.blog.exception;

public class BlogNotSavedException extends RuntimeException {
    public BlogNotSavedException(String message) {
        super(message);
    }

    public BlogNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogNotSavedException(Throwable cause) {
        super(cause);
    }
}

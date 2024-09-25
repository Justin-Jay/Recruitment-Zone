package za.co.recruitmentzone.blog.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class BlogException extends RzoneException {

    public BlogException(String failureReason) {
        super(failureReason);
    }

    public BlogException(String failureReason, Throwable cause) {
        super(failureReason, cause);
    }
}

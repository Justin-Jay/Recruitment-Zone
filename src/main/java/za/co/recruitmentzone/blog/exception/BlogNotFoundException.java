package za.co.recruitmentzone.blog.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class BlogNotFoundException extends RzoneException {
    public BlogNotFoundException(String message) {
        super(message);
    }
}

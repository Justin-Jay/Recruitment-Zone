package za.co.recruitmentzone.blog.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class BlogNotSavedException extends RzoneException {
    public BlogNotSavedException(String message) {
        super(message);
    }

}

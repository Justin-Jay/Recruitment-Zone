package za.co.recruitmentzone.util;

import jakarta.servlet.http.HttpServletRequest;

public class Constants {

    // Constants for error messages
    public static class ErrorMessages {
        public static final String INTERNAL_SERVER_ERROR = "Internal server error: Please contact system administrator";
    }

    public static  String applicationURL(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

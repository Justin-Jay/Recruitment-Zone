package za.co.recruitmentzone.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            log.info("Error {}", status);
            log.info("request getPathInfo {}", request.getPathInfo());
            log.info("request getContextPath {}", request.getPathInfo());
            log.info("Error with status code {} occurred", statusCode);
        }
        return "error";
    }

}


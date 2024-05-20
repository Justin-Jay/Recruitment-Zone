/*
package za.co.recruitmentzone.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private HttpSession session;

    // Define a pointcut expression that matches all methods in controller classes within the specified package
    @Pointcut("execution(* za.co.recruitmentzone.*Controller.*(..))")
    public void controllerMethods() {
        // This method doesn't have any behavior; it's just a placeholder for the pointcut expression
    }


    @Before("controllerMethods() && @annotation(modelAttribute)")
    public void addModelAttributeToSession(ModelAttribute modelAttribute) {
        String attributeName = modelAttribute.value();
        // Assuming that you have access to the ModelAndView through a ThreadLocal or other means,
        // you can access it here if needed
        Object attributeValue = mav.getModel().get(attributeName);
        // However, for simplicity, I'm skipping it here as it's not necessary for storing the attribute in the session
        Object attributeValue = null; // Placeholder, you need to define how to retrieve the attribute value
        if (attributeValue != null) {
            session.setAttribute(attributeName, attributeValue);
        }
    }
}*/

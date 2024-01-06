package za.co.recruitmentzone.employee.Events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.employee.entity.Employee;

@Component
public class EmployeeEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(EmployeeEventPublisher.class);

    public EmployeeEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public boolean publishNewEmployeeEvent(Employee employee,String applicationURL) {
        NewEmployeeEvent newEmployeeEvent = new NewEmployeeEvent(employee,applicationURL);
        log.info("Executing publishNewEmployeeEvent");
        try {
            eventPublisher.publishEvent(newEmployeeEvent);
            log.info("EVENT publishNewEmployeeEvent POSTED");
            return true;
        } catch (Exception e) {
            log.info("EVENT publishNewEmployeeEvent FAILED");
            log.info(e.getMessage());
            return false;
        }

    }



}

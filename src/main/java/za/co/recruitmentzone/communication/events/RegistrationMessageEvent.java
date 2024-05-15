package za.co.recruitmentzone.communication.events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.employee.entity.Employee;

public class RegistrationMessageEvent extends ApplicationEvent {
    private Employee employee;

    private String applicationURL;

    public RegistrationMessageEvent(Employee employee, String applicationURL) {
        super(employee);
        this.employee=employee;
        this.applicationURL=applicationURL;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getApplicationURL() {
        return applicationURL;
    }

    public void setApplicationURL(String applicationURL) {
        this.applicationURL = applicationURL;
    }
}

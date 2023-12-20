package za.co.RecruitmentZone.communication.entity;

import org.springframework.context.ApplicationEvent;
import za.co.RecruitmentZone.employee.entity.Employee;

public class RegistrationMessage extends ApplicationEvent {
    private Employee employee;

    private String applicationURL;

    public RegistrationMessage(Employee employee,String applicationURL) {
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

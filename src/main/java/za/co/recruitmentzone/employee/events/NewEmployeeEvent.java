package za.co.recruitmentzone.employee.events;

import org.springframework.context.ApplicationEvent;
import za.co.recruitmentzone.employee.entity.Employee;

public class NewEmployeeEvent extends ApplicationEvent {
    private Employee employee;
    String applicationURL;

    public NewEmployeeEvent(Employee employee, String applicationURL) {
        super(employee);
        this.employee = employee;
        this.applicationURL = applicationURL;
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

    @Override
    public String toString() {
        return "NewEmployeeEvent{" +
                "employee=" + employee +
                ", applicationURL='" + applicationURL + '\'' +
                '}';
    }

 /*   public String soureToString() {
        return "NewEmployeeEvent{" +
                "source=" + source +
                '}';
    }*/
}

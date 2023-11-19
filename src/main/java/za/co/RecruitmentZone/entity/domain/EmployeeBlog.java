package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="employee_blog")
public class EmployeeBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long employeeID;
    private Long bigID;

    public EmployeeBlog() {
    }

    public EmployeeBlog(Long employeeID, Long bigID) {
        this.employeeID = employeeID;
        this.bigID = bigID;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getBigID() {
        return bigID;
    }

    public void setBigID(Long bigID) {
        this.bigID = bigID;
    }
}

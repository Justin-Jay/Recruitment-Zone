package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="employee_blog")
public class EmployeeBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "employeeID")
    private Long employeeID;
    @Column(name = "blogID")
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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "EmployeeBlog{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", bigID=" + bigID +
                '}';
    }
}

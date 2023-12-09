package za.co.RecruitmentZone.employee.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_authorities")
public class EmployeeAuthorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "employee_id")
    private Long employee_id;
    @Column(name = "authority")
    private String authority;

    // Constructors, getters, and setters

    public EmployeeAuthorities() {
    }

    public EmployeeAuthorities(Long employee_id, String authority) {
        this.employee_id = employee_id;
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "EmployeeAuthorities{" +
                "id=" + id +
                ", employee_id=" + employee_id +
                ", authority='" + authority + '\'' +
                '}';
    }
}


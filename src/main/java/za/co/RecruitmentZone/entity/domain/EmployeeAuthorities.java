package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_authorities")
public class EmployeeAuthorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private String authority;

    // Constructors, getters, and setters

    public EmployeeAuthorities() {
    }

    public EmployeeAuthorities(Employee employee, String authority) {
        this.employee = employee;
        this.authority = authority;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getAuthority() {
        return authority;
    }


}


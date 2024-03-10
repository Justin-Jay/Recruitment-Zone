package za.co.recruitmentzone.employee.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import za.co.recruitmentzone.util.enums.ROLE;

import java.util.Objects;

@Entity
@Table(name = "AUTHORITIES")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ManyToOne()
    @JoinColumn(name = "employeeID")
    private Employee employee;
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    public Authority() {
    }
    public Authority(ROLE role) {
        this.role = role;
    }

    public Authority(Employee employee, ROLE role) {
        this.employee = employee;
        this.role = role;
    }

    public ROLE getRole() {
        return role;
    }


    @Override
    public String getAuthority() {
        return role.name();
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "employee=" + employee +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority authority)) return false;
        return Objects.equals(employee, authority.employee) && role == authority.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, role);
    }
}

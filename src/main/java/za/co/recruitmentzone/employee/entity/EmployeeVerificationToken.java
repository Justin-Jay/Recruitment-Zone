package za.co.recruitmentzone.employee.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEEVERIFICATIONTOKEN")
public class EmployeeVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @OneToOne
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public EmployeeVerificationToken() {
    }

    public EmployeeVerificationToken(String token) {
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public EmployeeVerificationToken(String token, LocalDateTime creationTime, Employee employee) {
        this.token = token;
        this.creationTime = creationTime;
        this.expirationTime = this.getTokenExpirationTime();
        this.employee = employee;
    }

    public LocalDateTime getTokenExpirationTime() {
        return LocalDateTime.now().plusMinutes(15);
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "EmployeeVerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", creationTime=" + creationTime +
                ", expirationTime=" + expirationTime +
                ", employee=" + employee +
                '}';
    }

    public boolean isExpired(){
        return expirationTime.isBefore(LocalDateTime.now());
    }
}

package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="employee_vacancy")
public class EmployeeVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "employeeID")
    private Long employeeID;
    @Column(name = "vacancyID")
    private Long vacancyID;

    public EmployeeVacancy() {
    }

    public EmployeeVacancy(Long employeeID, Long vacancyID) {
        this.employeeID = employeeID;
        this.vacancyID = vacancyID;
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

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    @Override
    public String toString() {
        return "EmployeeVacancy{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", vacancyID=" + vacancyID +
                '}';
    }
}

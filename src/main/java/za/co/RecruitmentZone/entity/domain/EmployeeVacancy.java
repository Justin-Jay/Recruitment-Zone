package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="employee_vacancy")
public class EmployeeVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long employeeID;
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
}

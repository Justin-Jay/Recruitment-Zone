package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name="vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vacancyID;
    private String jobTitle;

    public Vacancy() {
    }

    public Vacancy(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyID=" + vacancyID +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}

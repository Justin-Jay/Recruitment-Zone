package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicationID")
    private Long applicationID;

    private String date_received;

    private String submission_date;

    private Integer status;

    private Long  candidateID;

    private Long vacancyID;

    public Application() {
    }


}



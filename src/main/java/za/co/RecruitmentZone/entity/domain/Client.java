package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientID;
    private String name;
    private String industry;
/*    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    @JoinTable(
            name = "client_contact_person",
            joinColumns = @JoinColumn(name = "clientID"),
            inverseJoinColumns = @JoinColumn(name = "contactPersonID")
    )
    private List<ContactPerson> contactPeople;*/
/*    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    @JoinTable(
            name = "client_vacancy",
            joinColumns = @JoinColumn(name = "clientID"),
            inverseJoinColumns = @JoinColumn(name = "vacancyID")
    )
    private List<Vacancy> vacancies;*/
    public Client() {
    }
    public Client(String name, String industry) {
        this.name = name;
        this.industry = industry;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }

/*    public void addContactPerson(ContactPerson contactPerson){
        if (contactPeople ==null){
            contactPeople = new ArrayList<>();
        }
        contactPeople.add(contactPerson);
        contactPerson.setClient(this);
    }*/

/*    public void addVacancy(Vacancy vacancy){
        if (vacancies ==null){
            vacancies = new ArrayList<>();
        }
        vacancies.add(vacancy);
        vacancy.setClient(this);
    }*/
}

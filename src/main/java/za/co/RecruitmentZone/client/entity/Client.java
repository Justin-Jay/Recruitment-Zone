package za.co.RecruitmentZone.client.entity;

import jakarta.persistence.*;
import za.co.RecruitmentZone.candidate.dto.CandidateNoteDTO;
import za.co.RecruitmentZone.candidate.entity.CandidateNote;
import za.co.RecruitmentZone.client.dto.ClientNoteDTO;
import za.co.RecruitmentZone.util.Enums.Industry;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clientid")
    private Long clientID;
    private String name;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @Column(name="created")
    private Timestamp created;
    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    /*@JoinTable(
            name = "client_contact_person",
            joinColumns = @JoinColumn(name = "clientID"),
            inverseJoinColumns = @JoinColumn(name = "contactPersonID")
    )*/
    private Set<ContactPerson> contactPeople;
    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    /*@JoinTable(
            name = "client_vacancy",
            joinColumns = @JoinColumn(name = "clientID"),
            inverseJoinColumns = @JoinColumn(name = "vacancyID")
    )*/
    private Set<Vacancy> vacancies;

    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private Set<ClientNote> notes;

    public Client() {
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Client(String name, Industry industry) {
        this.name = name;
        this.industry = industry;
    }

    public Set<ContactPerson> getContactPeople() {
        return contactPeople;
    }

    public void setContactPeople(Set<ContactPerson> contactPeople) {
        this.contactPeople = contactPeople;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Set<ClientNote> getNotes() {
        return notes;
    }

    public void setNotes(Set<ClientNote> notes) {
        this.notes = notes;
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

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                '}';
    }

    public void addContactPerson(ContactPerson contactPerson) {
        if (contactPeople == null) {
            contactPeople = new HashSet<>();
        }
        contactPeople.add(contactPerson);
        contactPerson.setClient(this);
    }

    public void addVacancy(Vacancy vacancy) {
        if (vacancies == null) {
            vacancies = new HashSet<>();
        }
        vacancies.add(vacancy);
        vacancy.setClient(this);
    }

    public void addNote(ClientNote note) {
        if (notes == null) {
            notes = new HashSet<>();
        }
        notes.add(note);
        note.setClient(this);
    }

    public void addNote(ClientNoteDTO noteDTO){
        if (notes ==null){
            notes = new HashSet<>();
        }
        ClientNote newNote = new ClientNote(noteDTO.getComment());
        newNote.setClient(this);
        LocalDateTime dt = LocalDateTime.now();
        newNote.setDateCaptured(dt);
        newNote.setClient(this);
        notes.add(newNote);

    }
}

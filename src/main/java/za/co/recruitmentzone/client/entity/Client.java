package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.client.dto.ClientNoteDTO;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;
    @Column(name="name")
    private String name;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @Column(name="created")
    private LocalDateTime created;
    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private Set<ContactPerson> contactPeople;
    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private Set<Vacancy> vacancies;

    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private Set<ClientNote> notes;

    public Client() {
    }

    public Client(String name, Industry industry) {
        this.name = name;
        this.industry = industry;
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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

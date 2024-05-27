package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.client.dto.ClientNoteDTO;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {
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
    private List<ContactPerson> contactPeople;
    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<Vacancy> vacancies;

    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<ClientNote> notes;

    @OneToMany(mappedBy = "client",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<ClientFile> documents;

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

    public List<ContactPerson> getContactPeople() {
        return contactPeople;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public List<ClientFile> getDocuments() {
        return documents;
    }

    public int getContactPeopleCount() {
        if (contactPeople!=null)
            return contactPeople.size();
        else return 0;
    }


    public List<ClientNote> getNotes() {
        return notes;
    }


    public void addContactPerson(ContactPerson contactPerson) {
        if (contactPeople == null) {
            contactPeople = new ArrayList<>();
        }
        contactPeople.add(contactPerson);
        contactPerson.setClient(this);
    }

    public void addVacancy(Vacancy vacancy) {
        if (vacancies == null) {
            vacancies = new ArrayList<>();
        }
        vacancies.add(vacancy);
        vacancy.setClient(this);
    }


    public void AddDocument(ClientFile document){
        if (documents ==null){
            documents = new ArrayList<>() {
            };
        }
        documents.add(document);
        document.setClient(this);
    }

    public void addNote(ClientNoteDTO noteDTO){
        if (notes ==null){
            notes = new ArrayList<>();
        }
        ClientNote newNote = new ClientNote(noteDTO.getComment());
        newNote.setClient(this);
        LocalDateTime dt = LocalDateTime.now();
        newNote.setDateCaptured(dt);
        newNote.setClient(this);
        notes.add(newNote);

    }


    public String printClient() {
        return "Client{" +
                "clientID=" + clientID +
                ", name='" + name + '\'' +
                ", industry=" + industry +
                '}';
    }
}

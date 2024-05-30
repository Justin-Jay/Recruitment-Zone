package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.candidate.entity.CandidateNote;
import za.co.recruitmentzone.client.dto.ClientNoteDTO;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @Column(name = "created")
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
        if (this.contactPeople != null) {
            List<ContactPerson> sortedContacts = new ArrayList<>(this.contactPeople);
            Collections.sort(sortedContacts, new Comparator<ContactPerson>() {
                @Override
                public int compare(ContactPerson item1, ContactPerson item2) {
                    return item2.getCreated().compareTo(item1.getCreated());
                }
            });
            return sortedContacts;
        } else return new ArrayList<>();
    }

    public List<Vacancy> getVacancies() {
       if (this.vacancies!=null){
           List<Vacancy> sortedVacancies = new ArrayList<>(this.vacancies);
           Collections.sort(sortedVacancies, new Comparator<Vacancy>() {
               @Override
               public int compare(Vacancy item1, Vacancy item2) {
                   return item2.getCreated().compareTo(item1.getCreated());
               }
           });
           return sortedVacancies;
       }
       else return new ArrayList<>();
    }

    public List<ClientFile> getDocuments() {
        if (this.documents!=null){
            List<ClientFile> sortedDocuments = new ArrayList<>(this.documents);
            Collections.sort(sortedDocuments, new Comparator<ClientFile>() {
                @Override
                public int compare(ClientFile item1, ClientFile item2) {
                    return item2.getCreated().compareTo(item1.getCreated());
                }
            });
            return sortedDocuments;
        }
        else return new ArrayList<>();
    }

    public int getContactPeopleCount() {
        if (contactPeople != null)
            return contactPeople.size();
        else return 0;
    }

    public List<ClientNote> getNotes() {
        if (this.notes!=null){
            List<ClientNote> sortedNotes = new ArrayList<>(this.notes);
            Collections.sort(sortedNotes, new Comparator<ClientNote>() {
                @Override
                public int compare(ClientNote item1, ClientNote item2) {
                    return item2.getDateCaptured().compareTo(item1.getDateCaptured());
                }
            });
            return sortedNotes;
        }
        else return new ArrayList<>();
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


    public void AddDocument(ClientFile document) {
        if (documents == null) {
            documents = new ArrayList<>() {
            };
        }
        documents.add(document);
        document.setClient(this);
    }

    public void addNote(ClientNoteDTO noteDTO) {
        if (notes == null) {
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

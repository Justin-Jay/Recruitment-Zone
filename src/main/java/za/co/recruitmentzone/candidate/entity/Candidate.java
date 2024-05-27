package za.co.recruitmentzone.candidate.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.candidate.dto.CandidateNoteDTO;
import za.co.recruitmentzone.util.enums.EducationLevel;
import za.co.recruitmentzone.util.enums.Province;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CANDIDATE")
public class Candidate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateID;
    private String first_name;
    private String last_name;
    private String id_number;
    private String email_address;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private Province current_province;
    private String current_role;
    private String current_employer;
    private String seniority_level;
    @Enumerated(EnumType.STRING)
    private EducationLevel education_level;
    private Boolean relocation;
    @Column(name = "created")
    private LocalDateTime created;
    @OneToMany(mappedBy = "candidate",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Application> applications;
    @OneToMany(mappedBy = "candidate",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<CandidateNote> notes;

    @OneToMany(mappedBy = "candidate",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
            })
    private List<CandidateFile> documents;

    public Candidate() {
        // received
    }

    public Candidate(String first_name, String last_name,
                     String id_number, String email_address,
                     String phone_number, Province current_province,
                     String current_role, String current_employer,
                     String seniority_level, EducationLevel education_level, Boolean relocation) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.id_number = id_number;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.current_province = current_province;
        this.current_role = current_role;
        this.current_employer = current_employer;
        this.seniority_level = seniority_level;
        this.education_level = education_level;
        this.relocation = relocation;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<CandidateFile> getDocuments() {
        return documents;
    }


    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Province getCurrent_province() {
        return current_province;
    }

    public void setCurrent_province(Province current_province) {
        this.current_province = current_province;
    }

    public String getCurrent_role() {
        return current_role;
    }

    public void setCurrent_role(String current_role) {
        this.current_role = current_role;
    }

    public String getCurrent_employer() {
        return current_employer;
    }

    public void setCurrent_employer(String current_employer) {
        this.current_employer = current_employer;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
    }

    public EducationLevel getEducation_level() {
        return education_level;
    }

    public void setEducation_level(EducationLevel education_level) {
        this.education_level = education_level;
    }

    public Boolean getRelocation() {
        return relocation;
    }

    public void setRelocation(Boolean relocation) {
        this.relocation = relocation;
    }

    public List<Application> getApplications() {
        return applications;
    }


    public List<CandidateNote> getNotes() {
        return notes;
    }

    public void AddApplication(Application application) {
        if (applications == null) {
            applications = new ArrayList<>() {
            };
        }
        applications.add(application);
        application.setCandidate(this);
    }

    //AddDocument
    public void AddDocument(CandidateFile document) {
        if (documents == null) {
            documents = new ArrayList<>() {
            };
        }
        documents.add(document);
        document.setCandidate(this);
    }


    public void addNote(CandidateNoteDTO noteDTO) {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        CandidateNote newNote = new CandidateNote(noteDTO.getComment());
        newNote.setCandidate(this);
        newNote.setDateCaptured(noteDTO.getDateCaptured());
        newNote.setCandidate(this);
        notes.add(newNote);

    }


    public String printCandidate() {
        return "Candidate{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }


    public String printEducationLevel() {
        switch (education_level) {
            case MATRIC_CERTIFICATE -> {
                return "MATRIC CERTIFICATE";
            }
            case BACHELORS_DEGREE -> {
                return "BACHELORS DEGREE";
            }
            case NATIONAL_CERTIFICATE -> {
                return "NATIONAL CERTIFICATE";
            }

            case HONORS -> {
                return "HONORS";
            }

            case NQF_LEVEL_5 -> {
                return "NQF LEVEL 5";
            }
            case NQF_LEVEL_6 -> {
                return "NQF LEVEL 6";
            }
            default -> {
                return "UNKNOWN";
            }
        }
    }
}

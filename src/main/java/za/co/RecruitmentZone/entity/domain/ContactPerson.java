package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;

@Entity
@Table(name = "contact_person")
public class ContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactPersonID;
    private String first_name;
    private String last_name;
    @Email(message = "Email should be valid")
    private String email_address;
    private String land_line;
    private String cell_phone;
    @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
    })
    @JoinColumn(name = "clientid")
    private Client client;

    public ContactPerson() {
    }

    public ContactPerson(String first_name, String last_name, String email_address, String land_line, String cell_phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.land_line = land_line;
        this.cell_phone = cell_phone;
    }

    public Long getContactPersonID() {
        return contactPersonID;
    }

    public void setContactPersonID(Long contactPersonID) {
        this.contactPersonID = contactPersonID;
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

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getLand_line() {
        return land_line;
    }

    public void setLand_line(String land_line) {
        this.land_line = land_line;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "contactPersonID=" + contactPersonID +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_address='" + email_address + '\'' +
                ", land_line='" + land_line + '\'' +
                ", cell_phone='" + cell_phone + '\'' +
                ", client=" + client +
                '}';
    }
}

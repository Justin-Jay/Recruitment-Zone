package za.co.recruitmentzone.client.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "CONTACT_PERSON")
public class ContactPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersonID;
    @Column(name="first_name")
    private String first_name;
    @Column(name="last_name")
    private String last_name;
    @Column(name="email_address")
    private String email_address;
    @Column(name="designation")
    private String designation;

    @Column(name="land_line")
    private String land_line;
    @Column(name="cell_phone")
    private String cell_phone;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne( )
    @JoinColumn(name = "clientID")
    private Client client;

    //private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ContactPerson() {
    }

    public ContactPerson(String first_name, String last_name, String email_address, String land_line, String cell_phone,String designation) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.land_line = land_line;
        this.cell_phone = cell_phone;
        this.designation=designation;
        this.created = LocalDateTime.now();

    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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


    public String printContactPerson() {
        return "ContactPerson{" +
                "contactPersonID=" + contactPersonID +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email_address='" + email_address + '\'' +
                ", client=" + client.getName() +
                '}';
    }
}

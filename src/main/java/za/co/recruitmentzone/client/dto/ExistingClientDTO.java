package za.co.recruitmentzone.client.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import za.co.recruitmentzone.util.enums.Industry;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExistingClientDTO implements Serializable {

    private long clientID;
    private String name;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private LocalDateTime created;
    private long contactPeopleCount;

    public long getContactPeopleCount() {
        return contactPeopleCount;
    }

    public void setContactPeopleCount(long contactPeopleCount) {
        this.contactPeopleCount = contactPeopleCount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
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

    public String printExistingClientDTO() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                ", industry=" + industry +
                '}';
    }
}

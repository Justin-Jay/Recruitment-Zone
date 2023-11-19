package za.co.RecruitmentZone.entity.domain;

public class EmployeeDTO {
    public String first_name;

    public String last_name;

    public String contact_number;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, String contactNumber) {
        first_name = firstName;
        last_name = lastName;
        contact_number = contactNumber;
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

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
}

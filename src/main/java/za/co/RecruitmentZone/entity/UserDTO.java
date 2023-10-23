package za.co.RecruitmentZone.entity;

public class UserDTO {
    private Integer userID;
    private String first_name;
    private String last_name;
    private String email_address;
    private String username;

    public UserDTO() {
    }

    public UserDTO(Integer userID, String first_name, String last_name, String email_address, String username) {
        this.userID = userID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

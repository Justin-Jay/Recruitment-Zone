package za.co.RecruitmentZone.Recruiter;
import za.co.RecruitmentZone.Vacancy.Vacancy;
import za.co.RecruitmentZone.util.Email;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FirstName;
    private String LastName;
    private String password;
    private String role;
    private String UserName;
    private Email email;

    // Relationship: A recruiter can have multiple vacancies
    @OneToMany(mappedBy = "recruiter")
    private List<Vacancy> vacancies = new ArrayList<>();

    public Recruiter() {
    }

    public Recruiter(Long id, String firstName, String lastName, String password, String role, String userName, Email email, List<Vacancy> vacancies) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.password = password;
        this.role = role;
        UserName = userName;
        this.email = email;
        this.vacancies = vacancies;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}

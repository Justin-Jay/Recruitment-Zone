package za.co.recruitmentzone.employee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import za.co.recruitmentzone.blog.entity.Blog;
import za.co.recruitmentzone.client.entity.ClientNote;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "USERS")
public class Employee implements Principal, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeID")
    private Long employeeID;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @NaturalId(mutable = true)
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "contact_number")
    private String contact_number;
    @Column(name = "isEnabled")
    private boolean isEnabled;
    @Column(name="created")
    private Timestamp created;
    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private List<Authority> authorities;

    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private List<Blog> blogs;

    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER,
                cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
                })
    private List<Vacancy> vacancies;


    public Employee() {
    }

    public Employee(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Blog> getBlogs() {
     if(this.blogs!=null){
         List<Blog> sortedBlogs = new ArrayList<>(this.blogs);
         Collections.sort(sortedBlogs, new Comparator<Blog>() {
             @Override
             public int compare(Blog item1, Blog item2) {
                 return item2.getCreated().compareTo(item1.getCreated());
             }
         });
         return sortedBlogs;
     }
       return new ArrayList<>();
    }

    public List<Vacancy> getVacancies() {
       if (this.vacancies != null) {
           List<Vacancy> sortedVacancies = new ArrayList<>(this.vacancies);
           Collections.sort(sortedVacancies, new Comparator<Vacancy>() {
               @Override
               public int compare(Vacancy item1, Vacancy item2) {
                   return item2.getCreated().compareTo(item1.getCreated());
               }
           });
           return sortedVacancies;
       }
       return new ArrayList<>();
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }


    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    public void addBlog(Blog blog){
        if (blogs ==null){
            blogs = new ArrayList<>();
        }
        blogs.add(blog);
        blog.setEmployee(this);
    }

    public void addVacancy(Vacancy vacancy){
        if (vacancies ==null){
            vacancies = new ArrayList<>();
        }
        vacancies.add(vacancy);
        vacancy.setEmployee(this);
    }

    public void addAuthority(Authority authority){
        if (authorities ==null){
            authorities = new ArrayList<>();
        }
        authorities.add(authority);
        authority.setEmployee(this);
    }

    public void addAuthorities(Set<Authority> NewAuthorities){
        if (this.authorities ==null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.addAll(NewAuthorities);
        NewAuthorities.forEach(n->n.setEmployee(this));
    }


    public String printEmployee() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", username='" + username + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}

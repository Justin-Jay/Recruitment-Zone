package za.co.RecruitmentZone.employee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import za.co.RecruitmentZone.blog.entity.Blog;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import javax.security.auth.Subject;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class Employee implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeID")
    private Long employeeID;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    //    @NaturalId(mutable = true)
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "contact_number")
    private String contact_number;
    @Column(name = "enabled")
    private boolean isEnabled;
    @Column(name="created")
    private Timestamp created;
    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private List<Authority> authorities;

    @OneToMany(mappedBy = "employee",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
    private Set<Blog> blogs;

    @OneToMany(mappedBy = "employee",
                cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
                })
    private Set<Vacancy> vacancies;

    public Employee() {
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

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
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
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                '}';
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
            blogs = new HashSet<>();
        }
        blogs.add(blog);
        blog.setEmployee(this);
    }

    public void addVacancy(Vacancy vacancy){
        if (vacancies ==null){
            vacancies = new HashSet<>();
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


}

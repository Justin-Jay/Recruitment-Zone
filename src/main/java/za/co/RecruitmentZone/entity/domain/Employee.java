package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeID")
    private Long employeeID;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email_address")
    private String email_address;
    @Column(name = "contact_number")
    private String contact_number;

    @OneToMany(mappedBy = "employee",
            cascade = {
                    CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
            })
  /*  @JoinTable(
            name = "employee_blog",
            joinColumns = @JoinColumn(name = "employeeID"),
            inverseJoinColumns = @JoinColumn(name = "blogID")
    )*/
    private Set<Blog> blogs;

    @OneToMany(mappedBy = "employee",
                cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
                })
 /*   @JoinTable(
            name = "employee_vacancy",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancyID")
    )*/
    private Set<Vacancy> vacancies;

    public Employee() {
    }

    public Employee(String username, String first_name, String last_name, String email_address, String contact_number) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.contact_number = contact_number;
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

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                '}';
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


}

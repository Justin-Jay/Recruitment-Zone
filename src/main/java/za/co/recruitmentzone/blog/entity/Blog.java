package za.co.recruitmentzone.blog.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.employee.entity.Employee;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="BLOG")
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blogID")
    private Long blogID;
    private String blog_title;
    private String blog_description;
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
    @Column(name = "body", length = 65535)
    private String body;
    @Column(name="publish_date")
    private LocalDate publish_date;
    @Column(name="end_date")
    private LocalDate end_date;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public Blog() {
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Blog(BlogDTO blogDTO) {
    }


    public BlogStatus getStatus() {
        return status;
    }

    public void setStatus(BlogStatus status) {
        this.status = status;
    }

    public Long getBlogID() {
        return blogID;
    }

    public void setBlogID(Long blogID) {
        this.blogID = blogID;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_description() {
        return blog_description;
    }

    public void setBlog_description(String blog_description) {
        this.blog_description = blog_description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDate publish_date) {
        this.publish_date = publish_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getOwner(){
        return this.employee;
    }


    public String printBlog() {
        return "Blog{" +
                "blogID=" + blogID +
                ", blog_title='" + blog_title + '\'' +
                ", status=" + status +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                '}';
    }
}


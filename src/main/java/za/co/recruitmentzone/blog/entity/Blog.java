package za.co.recruitmentzone.blog.entity;

import jakarta.persistence.*;
import za.co.recruitmentzone.util.enums.BlogStatus;
import za.co.recruitmentzone.blog.dto.BlogDTO;
import za.co.recruitmentzone.employee.entity.Employee;

import java.time.LocalDateTime;

@Entity
@Table(name ="blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="blogID")
    private Long blogID;

    private String blog_title;
    private String blog_description;
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
    private String body;
    private String publish_date;
    private String end_date;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne(cascade = {
            CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH
    })
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public Blog() {
    }

    public Blog(String blog_title, String blog_description, String body,String publish_date,String end_date) {
        this.blog_title = blog_title;
        this.blog_description = blog_description;
        this.body = body;
        this.publish_date=publish_date;
        this.end_date=end_date;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Blog(BlogDTO blogDTO) {
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
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

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogID=" + blogID +
                ", blog_title='" + blog_title + '\'' +
                ", blog_description='" + blog_description + '\'' +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", employee=" + employee +
                '}';
    }
}


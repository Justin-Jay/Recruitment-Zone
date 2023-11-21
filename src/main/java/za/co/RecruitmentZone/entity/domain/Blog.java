package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name ="blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blogID")
    private Long blogID;
    @Column(name = "blog_title")
    private String blog_title;
    @Column(name="blogDescription")
    private String blog_description;
    @Column(name = "body")
    private String body;
    @Column(name = "employeeID")
    private Long employeeID;

    public Blog() {
    }

    public Blog(String blog_title, String blog_description, String body, Long employeeID) {
        this.blog_title = blog_title;
        this.blog_description = blog_description;
        this.body = body;
        this.employeeID = employeeID;
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

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogID=" + blogID +
                ", blog_title='" + blog_title + '\'' +
                ", blog_description='" + blog_description + '\'' +
                ", body='" + body + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}


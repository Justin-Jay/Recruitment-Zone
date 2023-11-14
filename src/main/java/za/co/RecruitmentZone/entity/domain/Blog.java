package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name ="blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blogID;
    private String blogTitle;
    private String blogDescription;
    @Column(name = "employeeID")
    private Long employeeID;
    public Blog() {
    }

    public Blog(String blogTitle, String blogDescription, Long employeeID) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.employeeID = employeeID;
    }

    public Long getBlogID() {
        return blogID;
    }


    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
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
                ", blogTitle='" + blogTitle + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}


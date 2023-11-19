package za.co.RecruitmentZone.entity.domain;

import jakarta.persistence.*;

@Entity
@Table(name ="blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blogID;
    private String blog_title;
    public Blog() {
    }

    public void setBlogID(Long blogID) {
        this.blogID = blogID;
    }

    public Long getBlogID() {
        return blogID;
    }

    public Blog(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blog_title='" + blog_title + '\'' +
                '}';
    }
}


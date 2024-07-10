package za.co.recruitmentzone.blog.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.recruitmentzone.util.enums.BlogStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BlogDTO implements Serializable {

    long blogID;
    @NotEmpty(message = "Must not be empty")
    private String blog_title;
    @NotEmpty(message = "Please enter description")
    private String blog_description;
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
    @NotEmpty(message = "Body cannot be empty")
    private String body;
    @FutureOrPresent(message = "Date cannot be in the past")
    @NotNull(message = "Please select a date")
    private LocalDate publish_date;
    @Future(message = "Date must be in the future")
    @NotNull(message = "Please select a date")
    private LocalDate end_date;

    private String employee;

    public BlogDTO() {
    }

    public BlogDTO(String blog_title, String blog_description, BlogStatus status, String body, LocalDate publish_date, LocalDate end_date, String employee) {
        this.blog_title = blog_title;
        this.blog_description = blog_description;
        this.status = status;
        this.body = body;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.employee = employee;
    }


    public String printBlogDTO() {
        return "BlogDTO{" +
                "blog_title='" + blog_title + '\'' +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", publish_date=" + publish_date +
                ", end_date=" + end_date +
                ", employee='" + employee + '\'' +
                '}';
    }
}


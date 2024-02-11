package za.co.recruitmentzone.blog.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import za.co.recruitmentzone.util.enums.BlogStatus;

import java.time.LocalDate;

public class BlogDTO {
    @NotEmpty(message = "Must not be empty")
    private String blog_title;
    @NotEmpty(message = "Please enter description")
    private String blog_description;
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
    @NotEmpty(message = "Body cannot be empty")
    private String body;
    @FutureOrPresent(message = "Date cannot be in the past")
    private LocalDate publish_date;
    @Future(message = "Date must be in the future")
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

    public BlogStatus getStatus() {
        return status;
    }

    public void setStatus(BlogStatus status) {
        this.status = status;
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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
                "blog_title='" + blog_title + '\'' +
                ", blog_description='" + blog_description + '\'' +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", publish_date=" + publish_date +
                ", end_date=" + end_date +
                ", employee='" + employee + '\'' +
                '}';
    }
}


package za.co.recruitmentzone.blog.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import za.co.recruitmentzone.util.enums.BlogImageType;

import java.io.Serializable;

@Data
public class BlogImageDTO implements Serializable {

    private long blogID;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Make selection")
    private BlogImageType blogImageType;

    @NotNull(message = "Please upload an image")
    private MultipartFile blogImage;

    public BlogImageDTO(long blogID) {
        this.blogID = blogID;
    }


    public String printBlogImageDTO() {
        return "BlogImageDTO{" +
                "blogID=" + blogID +
                ", blogImageType=" + (blogImageType!=null?blogImageType:"No Image Type") +
                ", blogImage Empty =" + (!blogImage.isEmpty()) +
                '}';
    }
}


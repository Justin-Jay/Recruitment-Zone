package za.co.recruitmentzone.vacancy.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class VacancyImageDTO implements Serializable {

    private long vacancyID;

    @NotNull(message = "Please upload an image")
    private MultipartFile vacancyImage;

    public VacancyImageDTO(long vacancyID) {
        this.vacancyID = vacancyID;
    }


}


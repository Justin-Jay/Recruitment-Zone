package za.co.RecruitmentZone.employee.entity;

import jakarta.persistence.*;
import org.threeten.bp.LocalDateTime;

public class VerificationRequest {

    private String token;
    private String name;

    public VerificationRequest(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VerificationRequest{" +
                "token=" + token +
                ", name='" + name + '\'' +
                '}';
    }
}

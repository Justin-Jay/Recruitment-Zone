package za.co.RecruitmentZone.entity;

import java.util.Set;

public class AuthoritiesDTO {
    private Set<String> roles;

    public AuthoritiesDTO() {
    }

    public AuthoritiesDTO(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

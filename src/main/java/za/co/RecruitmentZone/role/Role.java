package za.co.RecruitmentZone.role;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleID")
    private Integer roleID;

    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Integer roleID, String authority) {
        this.roleID = roleID;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

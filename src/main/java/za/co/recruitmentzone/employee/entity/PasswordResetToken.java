package za.co.recruitmentzone.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationTime;
    @OneToOne
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public PasswordResetToken(String token, Employee employee) {
        this.token = token;
        this.employee = employee;
        this.expirationTime = LocalDateTime.now().plusMinutes(30);
    }

    public boolean isExpired(){
        return expirationTime.isBefore(LocalDateTime.now());
    }

}

package za.co.RecruitmentZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import za.co.RecruitmentZone.Entity.ApplicationUser;
import za.co.RecruitmentZone.Entity.Role;
import za.co.RecruitmentZone.repository.RoleRepository;
import za.co.RecruitmentZone.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RecruitmentZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentZoneApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

			//if (roleRepository.findRoleByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser(1, "ADMIN", passwordEncoder.encode("password"));

            userRepository.save(admin);


        };
    }


}

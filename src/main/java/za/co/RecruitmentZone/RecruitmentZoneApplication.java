package za.co.RecruitmentZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.RecruitmentZone.entity.ApplicationUser;
import za.co.RecruitmentZone.entity.Role;
import za.co.RecruitmentZone.repository.RoleRepository;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class RecruitmentZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentZoneApplication.class, args);
    }

 /*   @Bean
    public CommandLineRunner run(RoleRepository roleRepository, ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            //if (roleRepository.findRoleByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));

            Role recruiterRole = roleRepository.save(new Role("RECRUITER"));

            Role recruitmentManagerRole = roleRepository.save(new Role("RECRUITMENT_MANAGER"));



            ApplicationUser admin = new ApplicationUser("ADMIN", passwordEncoder.encode("password"));

            ApplicationUser recruiter = new ApplicationUser("RECRUITER", passwordEncoder.encode("password"));
            ApplicationUser recruitmentManager = new ApplicationUser("RECRUITMENT_MANAGER", passwordEncoder.encode("password"));


            userRepository.save(admin);

            userRepository.save(recruiter);

            userRepository.save(recruitmentManager);

            ApplicationUser justin = new ApplicationUser();
            justin.setFirst_name("Justin");
            justin.setLast_name("Maboshego");
            justin.setEmail_address("Justin.Maboshego@kiunga.co.za");
            justin.setUsername("Justin.Maboshego@kiunga.co.za");
            justin.setPassword("Khum0Ramo@1992");
            Optional<Role> justinsRole = roleRepository.findRoleByAuthority("ADMIN");
            if (justinsRole.isPresent())
            { justin.setAuthorities(justinsRole.orElseGet(null));

            }


        };
    }

*/
}

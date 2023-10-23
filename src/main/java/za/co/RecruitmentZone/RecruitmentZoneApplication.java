package za.co.RecruitmentZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

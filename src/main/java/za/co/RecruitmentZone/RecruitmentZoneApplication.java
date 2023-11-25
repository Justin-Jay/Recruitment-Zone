package za.co.RecruitmentZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecruitmentZoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentZoneApplication.class, args);
    }

/*
    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (roleRepository.findRoleByAuthority("ADMIN").isPresent())
            {
                return;
            }

            Role adminRole = roleRepository.save(new Role("ADMIN"));

            roleRepository.save(adminRole);

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            ApplicationUser admin = new ApplicationUser("first_name","last_name","last_name", passwordEncoder.encode("password"), roles);

            userRepository.save(admin);


            // Role recruiterRole = roleRepository.save(new Role("RECRUITER"));

            // Role recruitmentManagerRole = roleRepository.save(new Role("RECRUITMENT_MANAGER"));

            //ApplicationUser recruiter = new ApplicationUser(recruiterRole, passwordEncoder.encode("password"));
            //ApplicationUser recruitmentManager = new ApplicationUser(recruitmentManagerRole, passwordEncoder.encode("password"));

            // userRepository.save(recruiter);

            // userRepository.save(recruitmentManager);
        };
    }
*/


    /*public static void main(String[] args)   {
		SpringApplication.run(MailManagerApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		EmailService service = context.getBean("EmailService", EmailService.class);

		ArrayList<String> emailAddresses = new ArrayList<>();
		emailAddresses.add("no-reply@property24.com");
		// emailAddresses.add("Sonng@musicafrica.com");

		// change the bellow for loop constraint in order to loop through multiple email addresses
		for(int i=0;i<1;i++){
			log.info("Email Count = "+i);
			log.info("Email Address to search: "+emailAddresses.get(i));
			service.deleteEmailBySender(emailAddresses.get(i));
		}


	}*/
}

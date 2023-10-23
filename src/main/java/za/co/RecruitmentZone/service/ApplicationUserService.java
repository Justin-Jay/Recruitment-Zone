package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.entity.*;
import za.co.RecruitmentZone.publisher.ApplicationUserEventPublisher;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;
import za.co.RecruitmentZone.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(ApplicationUserService.class);

    private ApplicationUserRepository userRepository;

    private RoleRepository roleRepository;

    private ApplicationUserEventPublisher userEventPublisher;


    public ApplicationUserService(ApplicationUserRepository userRepository, RoleRepository roleRepository, ApplicationUserEventPublisher userEventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("insider user service");
        return userRepository.findApplicationUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username invalid"));
    }

 // publish event for creation of user - this will send sign up link
    public String publishApplicationUserCreateEvent(String json) {
        ApplicationUserDTO newApplicationUser = new ApplicationUserDTO();
        newApplicationUser.setFirst_name("Justin");
        newApplicationUser.setLast_name("Maboshego");
        newApplicationUser.setEmail_address("Justin.Maboshego@Gmail.com");
        newApplicationUser.setRole("ADMIN");

        Optional<ApplicationUser>  existsAlready =  userRepository.findApplicationUserByUsername(newApplicationUser.getEmail_address());
        if (existsAlready.isPresent())
        {
            return "User Already exists ";
        }

        userEventPublisher.publishApplicationUserCreateEvent(newApplicationUser);


        return "successfully triggered event?";
    }


    // create a user instantly - Password is not safe at all
    public boolean createNewUser(String json) {
        try {
            Gson gson = new Gson();
            ApplicationUser newUser = gson.fromJson(json, ApplicationUser.class);

            newUser = userRepository.save(newUser);
            if (newUser!=null){
                return true;
            }
        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return false;
    }

/*    public User_ findUserByID(Long id){
        Optional<User_> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);

    }*/




}

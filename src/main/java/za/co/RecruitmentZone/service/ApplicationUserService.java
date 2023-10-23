package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class ApplicationUserService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(ApplicationUserService.class);

    private final ApplicationUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final ApplicationUserEventPublisher userEventPublisher;

    public ApplicationUserService(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ApplicationUserEventPublisher userEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("insider user details service");
        return userRepository.findApplicationUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username invalid"));
    }

    // publish event for creation of user - this will send sign up link
    public String publishApplicationUserCreateEvent(UserDTO user) {
        Optional<User> existsAlready = userRepository.findApplicationUserByUsername(user.getEmail_address());
        if (existsAlready.isPresent()) {
            return "User Already exists ";
        }
        userEventPublisher.publishApplicationUserCreateEvent(user);
        return "successfully triggered event?";
    }


    // create a user instantly - Password is not safe at all
/*
    public boolean createNewUser(UserDTO user) {
        try {
            */
/*

                    ApplicationUser newApplicationUser = new ApplicationUser();
        newApplicationUser.setFirst_name("Justin");
        newApplicationUser.setLast_name("Maboshego");
        newApplicationUser.setEmail_address("Justin.Maboshego@Gmail.com");
       newApplicationUser.setRole("ADMIN");

            *//*

            Gson gson = new Gson();

            User newUser = new User();
            newUser = userRepository.save(newUser);
            if (newUser != null) {
                return true;
            }
        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
        return false;
    }
*/

/*    public User_ findUserByID(Long id){
        Optional<User_> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);

    }*/
public boolean createNewRestUser(User user) {
    try {
        user = userRepository.save(user);

        if (user!=null) {
            return true;
        }
    } catch (Exception e) {
        log.info("Unable to save" + e.getMessage());
    }
    return false;
}


    public void updateUserAuthorities(Integer id, Set<String> newRoles) throws Exception {
        // Fetch the user by ID
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        Set<Role> updatedRoles = new HashSet<>();
        for (String roleName : newRoles) {
            Role optionalRole = roleRepository.findByName(roleName);
            if (optionalRole!=null) {
                throw new Exception("Role not found: " + roleName);
            }
            updatedRoles.add(optionalRole);
        }

        // Update the authorities of the user
        user.setAuthorities(updatedRoles);

        // Save the updated user back to the repository
        userRepository.save(user);
    }


    public void updateUser(Integer id, UserDTO userDTO) throws Exception {
        // Fetch the existing user by ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found for ID: " + id));

        // Update the user fields based on the DTO
        existingUser.setFirst_name(userDTO.getFirst_name());
        existingUser.setLast_name(userDTO.getLast_name());
        existingUser.setEmail_address(userDTO.getEmail_address());


        // Save the updated user back into the repository
        userRepository.save(existingUser);
    }
}

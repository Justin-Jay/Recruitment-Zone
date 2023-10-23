package za.co.RecruitmentZone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.RecruitmentZone.repository.ApplicationUserRepository;



@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(ApplicationUserService.class);

    ApplicationUserRepository userRepository;

    public ApplicationUserService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("insider user service");
        return userRepository.findApplicationUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username invalid"));
    }


/*    public void addUser() {
        User_ newUser = new User_();
        newUser.setFirst_name("Johnny");
        newUser.setPhone_number("0109872228");
        newUser.setLast_name("Papa");
        userRepository.save(newUser);
    }

    public void process(String json) {
        try {
            Gson gson = new Gson();

            User_ newUser = gson.fromJson(json, User_.class);
            userRepository.save(newUser);

            log.info("User Saved");

        } catch (Exception e) {
            log.info("Unable to save" + e.getMessage());
        }
    }

    public User_ findUserByID(Long id){
        Optional<User_> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);

    }*/


}

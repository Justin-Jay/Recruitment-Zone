package za.co.recruitmentzone.employee.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import za.co.recruitmentzone.configuration.WebSecurityConfig;
import za.co.recruitmentzone.employee.repository.EmployeeRepository;

import java.util.Optional;

@Configuration
public class EmployeeDetailsService implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(EmployeeDetailsService.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user request: User --> {}",username);
        Optional<Employee> user = employeeRepository.findEmployeeByEmailIgnoreCase(username);
        return user.map(EmployeeDetails::new).orElseThrow(()->new UsernameNotFoundException("User Does Not Exist"));
    }
}

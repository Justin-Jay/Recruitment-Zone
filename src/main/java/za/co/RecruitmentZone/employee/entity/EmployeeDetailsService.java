package za.co.RecruitmentZone.employee.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import za.co.RecruitmentZone.employee.repository.EmployeeRepository;

import java.util.Optional;

@Configuration
public class EmployeeDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> user = employeeRepository.findEmployeeByEmailIgnoreCase(username);
        return user.map(EmployeeDetails::new).orElseThrow(()->new UsernameNotFoundException("User Does Not Exist"));
    }
}

package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Employee;
import za.co.RecruitmentZone.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public Optional<Employee> findByUsername(String username) {
        return  employeeRepository.findByUsername(username);
    }
    // getEmployees
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

}

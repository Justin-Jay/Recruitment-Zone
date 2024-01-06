package za.co.recruitmentzone.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.employee.Events.EmployeeEventPublisher;
import za.co.recruitmentzone.employee.entity.Authority;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.dto.EmployeeDTO;
import za.co.recruitmentzone.employee.exception.UserAlreadyExistsException;
import za.co.recruitmentzone.employee.repository.EmployeeRepository;
import za.co.recruitmentzone.util.Enums.ROLE;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static za.co.recruitmentzone.util.Enums.ROLE.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeEventPublisher eventPublisher;

    @Value("${domain.co.za}")
    String domainDotZa;

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeEventPublisher eventPublisher) {
        this.employeeRepository = employeeRepository;
        this.eventPublisher = eventPublisher;
    }

    public Employee createEmployee(EmployeeDTO employeeDTO, String applicationURL) throws UserAlreadyExistsException {
        String userName = createUserNameAndEmail(employeeDTO.getFirst_name(), employeeDTO.getLast_name());
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmailIgnoreCase(userName);
        Employee newEmp = new Employee();
        if (employeeOptional.isPresent()) {
            throw new UserAlreadyExistsException("User Already Exists");
        } else {

            newEmp.setFirst_name(employeeDTO.getFirst_name().strip());
            newEmp.setPassword(employeeDTO.getPassword());
            newEmp.setLast_name(employeeDTO.getLast_name());
            newEmp.setContact_number(employeeDTO.getContact_number());
            newEmp.setUsername(userName);
            newEmp.setEmail(userName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().format(formatter));
            newEmp.setCreated(timestamp);

            String role = employeeDTO.getRole();
            ROLE chosenRole = getRole(role);
            log.info("role selected {}", chosenRole);
            newEmp.addAuthority(new Authority(chosenRole));

            Employee emp = employeeRepository.save(newEmp);
            log.info("Employee Created {}", emp);
            // publish new employee Event
            eventPublisher.publishNewEmployeeEvent(emp, applicationURL);
        }
        return newEmp;
    }

    public ROLE getRole(String name) {
        ROLE role = null;
        switch (name) {
            case "ROLE_ADMIN" -> {
                role = ROLE_ADMIN;
            }
            case "ROLE_MANAGER" -> {
                role = ROLE_MANAGER;
            }
            case "ROLE_EMPLOYEE" -> {
                role = ROLE_EMPLOYEE;
            }
            case "ROLE_GUEST" -> {
                role = ROLE_GUEST;
            }
        }
        ;
        return role;
    }

    public Set<Authority> mapRolesToAuthorities(List<ROLE> roles) {
        return roles.stream()
                .map(Authority::new)
                .collect(Collectors.toSet());
    }

    public void saveUpdatedEmployee(Employee updated) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeRepository.findById(updated.getEmployeeID());
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            if (!updated.getFirst_name().equalsIgnoreCase(employee.getFirst_name())) {
                employee.setFirst_name(updated.getFirst_name());
            }

            if (!updated.getContact_number().equalsIgnoreCase(employee.getContact_number())) {
                employee.setContact_number(updated.getContact_number());
            }

            if (!updated.getLast_name().equalsIgnoreCase(employee.getLast_name())) {
                employee.setLast_name(updated.getLast_name());
            }

        }
        employeeRepository.save(employee);
    }


    public String createUserNameAndEmail(String firstName, String LastName) {
        return firstName + "." + LastName + "@" + domainDotZa;
    }

    public Optional<Employee> findEmployeeByEmail(String username) {
        return employeeRepository.findEmployeeByEmailIgnoreCase(username);
    }


    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByID(Long employeeID) {
        Optional<Employee> op = employeeRepository.findById(employeeID);
        return op.orElse(null);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeByName(String name) {
        Optional<Employee> op = employeeRepository.findEmployeeByName(name);
        if (op.isPresent()) {
            return op.get();
        }
        return null;
    }

}

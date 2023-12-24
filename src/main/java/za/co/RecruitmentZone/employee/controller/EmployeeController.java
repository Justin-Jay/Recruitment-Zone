package za.co.RecruitmentZone.employee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.RecruitmentZone.employee.entity.Authority;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.dto.EmployeeDTO;
import za.co.RecruitmentZone.employee.entity.EmployeeVerificationToken;
import za.co.RecruitmentZone.employee.entity.VerificationRequest;
import za.co.RecruitmentZone.employee.exception.TokenTimeOutException;
import za.co.RecruitmentZone.employee.exception.UserAlreadyExistsException;
import za.co.RecruitmentZone.employee.service.TokenVerificationService;
import za.co.RecruitmentZone.service.RecruitmentZoneService;
import za.co.RecruitmentZone.employee.service.EmployeeService;
import za.co.RecruitmentZone.util.Enums.ROLE;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final TokenVerificationService tokenVerificationService;

    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(RecruitmentZoneService recruitmentZoneService, TokenVerificationService tokenVerificationService, PasswordEncoder passwordEncoder, EmployeeService employeeService) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.tokenVerificationService = tokenVerificationService;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
    }

    @GetMapping("/employee-admin")
    public String employees(Model model) {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            allEmployees = getEmployees();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employees, retrieving all employees ");
        }
        model.addAttribute("employees", allEmployees);
        return "fragments/employee/employee-admin";
    }


    @PostMapping("/view-employee")
    public String showEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        Employee optionalEmployee = findEmployeeByID(employeeID);
        log.info("Looking for {}", employeeID);
        log.info(optionalEmployee.toString());
        model.addAttribute("employee", optionalEmployee);
        return "fragments/employee/view-employee";
    }

    @GetMapping("/add-employee")
    public String showCreateEmployeeForm(Model model, Principal principal) {
        EmployeeDTO newDto = new EmployeeDTO();
        String agent = principal.getName();
        newDto.setAdminAgent(agent);
        model.addAttribute("employeeDTO", newDto);
        List<ROLE> auths = loadAuthorities(agent);
        log.info("EMPLOYEE LOADING ROLES FOR AGENT {}", agent);
        log.info("AVAILABLE ROLES {}", auths);
        model.addAttribute("availableRoles", auths);
        return "fragments/employee/add-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO, BindingResult bindingResult,
                               Model model,
                               final HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeDTO", new EmployeeDTO());
            List<ROLE> auths = loadAuthorities(employeeDTO.getAdminAgent());
            log.info("EMPLOYEE LOADING AUTHORITIES {}", employeeDTO.getAdminAgent());
            model.addAttribute("myAuthorities", auths);
            model.addAttribute("message", "Employee BINDING FAILED");
            return "fragments/employee/add-employee";
        }
        try {
            saveNewEmployee(employeeDTO, request);
            redirectAttributes.addFlashAttribute("message", "Employee Added Successfully");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            log.info(userAlreadyExistsException.toString());
            model.addAttribute("employeeDTO", new EmployeeDTO());
            List<ROLE> auths = loadAuthorities(employeeDTO.getAdminAgent());
            log.info("EMPLOYEE LOADING AUTHORITIES {}", employeeDTO.getAdminAgent());
            model.addAttribute("myAuthorities", auths);
            model.addAttribute("message", userAlreadyExistsException.getFailureReason());
            return "fragments/employee/add-employee";
        }
        return "redirect:/Employee/employee-admin";
    }


    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        Employee employee = findEmployeeByID(employeeID);
        log.info("Employee has been loaded");
        model.addAttribute("employee", employee);
        return "fragments/employee/update-employee";
    }

    @PostMapping("/save-updated-employee")
    public String saveUpdatedEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/employee/update-employee";
        }
        employeeService.saveUpdatedEmployee(employee);
        return "redirect:/Employee/employee-admin";
    }

    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@ModelAttribute VerificationRequest verificationRequest, Model model) {
        String token = verificationRequest.getToken();
        String name = verificationRequest.getName();

        // Your existing logic here
        log.info("Received Token {}", token);
        log.info("verificationRequest {}", verificationRequest);

        try {
            String verificationResult = tokenVerificationService.verifyToken(name, token);
            model.addAttribute("message", verificationResult);
        } catch (TokenTimeOutException tokenTimeOutException) {
            log.info("");
            return "log-in";
        }
        model.addAttribute("tokenMessage", "Verification failed");
        return "fragments/employee/email-verified";
    }

    public List<ROLE> loadAuthorities(String principalEmail) {
        log.info("principal email: {}", principalEmail);
        Employee e = recruitmentZoneService.findEmployeeByEmail(principalEmail);
        return recruitmentZoneService.getAuthorities(e);
    }

    public void saveNewEmployee(EmployeeDTO employeeDTO, HttpServletRequest request) throws UserAlreadyExistsException {
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employeeService.createEmployee(employeeDTO, applicationURL(request));
    }

    public String applicationURL(HttpServletRequest request) {

        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public Employee findEmployeeByID(Long employeeID) {
        Employee employee = employeeService.findEmployeeByID(employeeID);
        return employee;
    }

}

package za.co.RecruitmentZone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
public class HTMXController {
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(HTMXController.class);

    public HTMXController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/htmx/employee")
    public String htmxEmployee(Model model) {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            allEmployees = employeeService.getEmployees();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employees, retrieving all employees ");
        }
        model.addAttribute("employees", allEmployees);
        return "fragments/employee/employee";
    }
}

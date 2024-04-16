package za.co.recruitmentzone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/htmx")
public class HTMXController {
    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(HTMXController.class);

    public HTMXController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/TestPage1")
    public String index(Model model) {
        return "/htmxTesting";
    }


    @PostMapping("/employee")
    public String htmxEmployee(Model model) {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            allEmployees = employeeService.getEmployees();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employees, retrieving all employees ");
        }
        model.addAttribute("employees", allEmployees);
        return "/fragments/employee/employee";
    }
}

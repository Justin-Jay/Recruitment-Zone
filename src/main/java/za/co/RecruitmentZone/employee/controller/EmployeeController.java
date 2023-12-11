package za.co.RecruitmentZone.employee.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.dto.EmployeeDTO;
import za.co.RecruitmentZone.service.RecruitmentZoneService;
import za.co.RecruitmentZone.employee.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    private final RecruitmentZoneService recruitmentZoneService;

    private final EmployeeService employeeService;
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(RecruitmentZoneService recruitmentZoneService, EmployeeService employeeService) {
        this.recruitmentZoneService = recruitmentZoneService;
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


    @GetMapping("/add-employee")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "fragments/employee/add-employee";
    }
    @PostMapping("/view-employee")
    public String showEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        Employee optionalEmployee = findEmployeeByID(employeeID);
        log.info("Looking for {}", employeeID);
        log.info(optionalEmployee.toString());
        model.addAttribute("employee", optionalEmployee);
        return "fragments/employee/view-employee";
    }
    @PostMapping("/save-employee")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO")EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/employee/add-employee";
        }
        saveNewEmployee(employeeDTO);
        return "redirect:/employee-admin";
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
        saveUpdatedEmployee(employee);
        return "redirect:/employee-admin";
    }


    public void saveNewEmployee(EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
    }

    public void saveUpdatedEmployee(Employee updated) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(updated.getEmployeeID());
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
        employeeService.save(employee);
    }

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    public Employee findEmployeeByID(Long employeeID) {
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByID(employeeID);
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }
        return employee;
    }

}

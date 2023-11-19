package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.domain.Blog;
import za.co.RecruitmentZone.entity.domain.Employee;
import za.co.RecruitmentZone.entity.domain.EmployeeDTO;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("/")
public class EmployeeController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // Employee
    @GetMapping("/employee-admin")
    public String employees(Model model) {
        List<Employee> allEmployees = new ArrayList<>();
        try {
            allEmployees = recruitmentZoneService.getEmployees();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employees, retrieving all employees ");
        }
        model.addAttribute("employees", allEmployees);
        return "employee-admin";
    }
    @GetMapping("/add-employee")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "fragments/employee/add-employee";
    }

    @PostMapping("/view-employee")
    public String showEmployee(@RequestParam("id") Long id, Model model) {
        Employee optionalEmployee = recruitmentZoneService.findEmployeeByID(id);
        log.info("Looking for {}", id);
        log.info(optionalEmployee.toString());
        model.addAttribute("employee", optionalEmployee);
        return "fragments/employee/view-employee";
    }

    @PostMapping("/save-employee")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO")EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/employee/add-employee";
        }
        recruitmentZoneService.saveEmployee(employeeDTO);
        return "redirect:employee-admin";
    }

    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam("id") Long id, Model model) {
        Employee employee = recruitmentZoneService.findEmployeeByID(id);
        model.addAttribute("employee", employee);
        return "fragments/employee/update-employee";
    }


    @PostMapping("/save-updated-employee")
    public String saveUpdatedEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/employee/update-employee";
        }
        recruitmentZoneService.saveEmployee(employee);
        return "redirect:employee-admin";
    }


//    @PostMapping("/save-updated-employee")
//    public String saveUpdatedEmployee(@Valid @ModelAttribute()EmployeeDTO employeeDTO,Long id, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "fragments/employee/update-employee";
//        }
//        recruitmentZoneService.updateExistingEmployee(id,employeeDTO);
//        return "redirect:employee-admin";
//    }


}

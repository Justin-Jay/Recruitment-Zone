package za.co.recruitmentzone.employee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.dto.EmployeeDTO;
import za.co.recruitmentzone.employee.entity.VerificationRequest;
import za.co.recruitmentzone.employee.exception.*;
import za.co.recruitmentzone.employee.service.TokenVerificationService;
import za.co.recruitmentzone.service.RecruitmentZoneService;
import za.co.recruitmentzone.util.enums.ROLE;

import java.security.Principal;
import java.util.List;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

    private  final RecruitmentZoneService recruitmentZoneService;
    private final TokenVerificationService tokenVerificationService;

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(RecruitmentZoneService recruitmentZoneService, TokenVerificationService tokenVerificationService) {
        this.recruitmentZoneService = recruitmentZoneService;
        this.tokenVerificationService = tokenVerificationService;
    }

    // message , saveNewEmployeeResponse , internalServerError , employee  , findEmployeeByIDResponse
    @GetMapping("/employee-admin")
    public String employees(Model model, @ModelAttribute("internalServerError") String internalServerError, @ModelAttribute("message") String message, @ModelAttribute("saveNewEmployeeResponse") String saveNewEmployeeResponse) {
        try {
            recruitmentZoneService.findAllEmployees(model);
            model.addAttribute("message", message);
            model.addAttribute("saveNewEmployeeResponse", saveNewEmployeeResponse);
            model.addAttribute("internalServerError", internalServerError);
        } catch (Exception e) {
            log.info("<-- employees -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);

        }
        log.info("<-- showVacancyApplicationForm --> model: \n {}", model.asMap().toString());
        return "fragments/employee/employee-admin";
    }

    // internalServerError ,   employee , findEmployeeByIDResponse
    @PostMapping("/view-employee")
    public String showEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        try {
            log.info("Looking for {}", employeeID);
            recruitmentZoneService.findEmployeeByID(employeeID, model);
        } catch (Exception e) {
            log.info("<-- showEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            log.info("<-- showEmployee --> model: \n {}", model.asMap().toString());
        }
        log.info("<-- showEmployee --> model: \n {}", model.asMap().toString());
        return "fragments/employee/view-employee";
    }
    // rolesNotFoundException, employeeNotFound , authorities , employeeDTO, internalServerError

    @GetMapping("/add-employee")
    public String showCreateEmployeeForm(Model model, Principal principal) {
        try {
            recruitmentZoneService.loadAuthorities(principal, model);
        } catch (Exception e) {
            log.info("<-- showCreateEmployeeForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);

        }
        log.info("<-- showCreateEmployeeForm --> model: \n {}", model.asMap().toString());
        return "fragments/employee/add-employee";
    }

    // rolesNotFoundException, employeeNotFound , authorities , employeeDTO, internalServerError
    @PostMapping("/save-employee")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO, BindingResult bindingResult,
                               Model model,
                               final HttpServletRequest request,
                               RedirectAttributes redirectAttributes,Principal principal) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.loadAuthorities(principal, model);
            model.addAttribute("message", "Employee BINDING FAILED");
            log.info("<-- saveEmployee --> model: \n {}", model.asMap().toString());
            return "fragments/employee/add-employee";
        }
        try {
            recruitmentZoneService.saveNewEmployee(employeeDTO, request, model, redirectAttributes,principal);
        } catch (Exception e) {
            log.info("<-- saveEmployee -->  Exception \n {}",e.getMessage());
            recruitmentZoneService.loadAuthorities(principal, model);
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            log.info("<-- saveEmployee --> model: \n {}", model.asMap().toString());
            return "fragments/employee/add-employee";
        }
        log.info("<-- saveEmployee --> redirectAttributes: \n {}", redirectAttributes.asMap().toString());
        return "redirect:/Employee/employee-admin";
    }
    //   internalServerError

    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        try {
            recruitmentZoneService.findEmployeeByID(employeeID, model);
        } catch (Exception e) {
            log.info("<-- updateEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);

        }
        log.info("<-- updateEmployee --> model: \n {}", model.asMap().toString());
        return "fragments/employee/update-employee";
    }
    //   internalServerError
    @PostMapping("/save-updated-employee")
    public String saveUpdatedEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fragments/employee/update-employee";
        }
        try {
            recruitmentZoneService.saveExistingEmployee(employee, redirectAttributes);
        } catch (Exception e) {
            log.info("<-- saveUpdatedEmployee -->  Exception \n {}",e.getMessage());
            redirectAttributes.addFlashAttribute("internalServerError", INTERNAL_SERVER_ERROR);

        }
        log.info("<-- saveUpdatedEmployee --> redirectAttributes: \n {}", redirectAttributes.asMap().toString());
        return "redirect:/Employee/employee-admin";
    }

    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@ModelAttribute VerificationRequest verificationRequest, Model model) {
        try {
            employeeVerification(verificationRequest,model);
        } catch (Exception e) {
            log.info("<-- verifyEmail -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            log.info("<-- verifyEmail --> model: \n {}", model.asMap().toString());
            return "log-in";
        }
        log.info("<-- verifyEmail --> model: \n {}", model.asMap().toString());
        return "fragments/employee/email-verified";
    }
//  message , verificationResponse , tokenMessage
    public void employeeVerification(VerificationRequest verificationRequest,Model model) {
        String token = verificationRequest.getToken();
        String name = verificationRequest.getName();
        // Your existing logic here
        log.info("Received Token {}", token);
        log.info("verificationRequest {}", verificationRequest);
        try {
            String verificationResult = tokenVerificationService.verifyToken(name, token);
            model.addAttribute("message", verificationResult);
        } catch (TokenTimeOutException tokenTimeOutException) {
            log.info("<-- Token Time out exception --> {}",tokenTimeOutException.getMessage());
            model.addAttribute("verificationResponse",tokenTimeOutException.getMessage());
            model.addAttribute("tokenMessage", tokenTimeOutException.getMessage());
        }
    }

}

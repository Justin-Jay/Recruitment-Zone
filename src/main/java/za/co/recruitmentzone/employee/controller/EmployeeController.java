package za.co.recruitmentzone.employee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.dto.EmployeeDTO;
import za.co.recruitmentzone.service.RecruitmentZoneService;

import java.security.Principal;

import static za.co.recruitmentzone.util.Constants.ErrorMessages.INTERNAL_SERVER_ERROR;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

    private  final RecruitmentZoneService recruitmentZoneService;

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }


    @GetMapping("/employee-admin")
    public String employees(Model model) {
        try {
            recruitmentZoneService.findAllEmployees(model);
        } catch (Exception e) {
            log.info("<-- employees -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // employeeList , loadEmployeesResponse , internalServerError
        return "fragments/employee/employee-admin";
    }


    @PostMapping("/view-employee")
    public String showEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        try {
            log.info("Looking for {}", employeeID);
            recruitmentZoneService.findEmployeeByID(employeeID, model);
        } catch (Exception e) {
            log.info("<-- showEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // employee , findEmployeeByIDResponse , internalServerError
        return "fragments/employee/view-employee";
    }


    @PostMapping("/add-employee")
    public String showCreateEmployeeForm(Model model, Principal principal) {
        try {
            recruitmentZoneService.loadAuthorities(principal, model);
        } catch (Exception e) {
            log.info("<-- showCreateEmployeeForm -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // employeeDTO , authorities , loadAuthoritiesResponse , internalServerError
        return "fragments/employee/add-employee";
    }

    // rolesNotFoundException, employeeNotFound , authorities , employeeDTO, internalServerError
    @PostMapping("/save-employee")
    public String saveEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO, BindingResult bindingResult,
                               Model model,
                               final HttpServletRequest request,Principal principal) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.loadAuthorities(principal, model);
            model.addAttribute("bindingResult", "Employee BINDING FAILED");
            return "fragments/employee/add-employee";
        }
        try {
            recruitmentZoneService.saveNewEmployee(employeeDTO, request, model, principal);

        } catch (Exception e) {
            log.info("<-- saveEmployee -->  Exception \n {}",e.getMessage());
            recruitmentZoneService.loadAuthorities(principal, model);
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            return "fragments/employee/add-employee";
        }
        // saveNewEmployeeResponse  , employeeDTO , employeeList
       // return "fragments/employee/employee-admin";
        return "fragments/employee/add-employee";
    }


    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam("employeeID") Long employeeID, Model model) {
        try {
            recruitmentZoneService.findEmployeeByID(employeeID, model);
        } catch (Exception e) {
            log.info("<-- updateEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);

        }
        return "fragments/employee/update-employee";
    }
    //   save-updated-employee //
    @PostMapping("/save-updated-employee")
    public String saveUpdatedEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model,Principal principal) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.findAllEmployees(model);
            return "fragments/employee/update-employee";
        }
        try {
            recruitmentZoneService.saveExistingEmployee(principal,employee, model);
        } catch (Exception e) {
            log.info("<-- saveUpdatedEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
         // saveExistingEmployeeResponse
        return "fragments/employee/view-employee";
    }


    @PostMapping("/activate-employee")
    public String employeeActivation(@RequestParam("employeeID") Long employeeID, Model model) {
        try {
            recruitmentZoneService.findEmployeeByID(employeeID, model);
        } catch (Exception e) {
            log.info("<-- updateEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/employee/activate-employee";
    }

    @PostMapping("/enable-employee")
    public String enableEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model,Principal principal) {
        if (bindingResult.hasErrors()) {
            recruitmentZoneService.findAllEmployees(model);
            return "fragments/employee/activate-employee";
        }
        try {
            recruitmentZoneService.enableEmployee(principal,employee, model);
        } catch (Exception e) {
            log.info("<-- activateEmployee -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        // enableEmployeeResponse
        return "fragments/employee/view-employee";
    }

// activate-employee

    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token,Model model) {
        try {
            log.info("<--- verifyEmail {} --->",token);
            recruitmentZoneService.employeeVerification(token,model);
        } catch (Exception e) {
            log.info("<-- verifyEmail -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
            return "log-in";
        }
        // employeeVerificationResponse , internalServerError
        return "fragments/employee/email-verified";
    }


  /*  @GetMapping("/password-reset-request")
    public String passwordResetRequest(@RequestParam("employeeID") Long employeeID, Model model, HttpServletRequest request){
        try {
            recruitmentZoneService.requestPasswordReset(employeeID,model,request);
        } catch (Exception e) {
            log.info("<-- employees -->  Exception \n {}",e.getMessage());
            model.addAttribute("internalServerError", INTERNAL_SERVER_ERROR);
        }
        return "fragments/employee/employee-admin";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest request){
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")){
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()){
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }



    @GetMapping("/forgot-password-request")
    public String forgotPasswordForm(){
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordRequest(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        Optional<User> user= userService.findByEmail(email);
        if (user.isEmpty()){
            return  "redirect:/registration/forgot-password-request?not_fond";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
        //send password reset verification email to the user
        String url = UrlUtil.getApplicationUrl(request)+"/registration/password-reset-form?token="+passwordResetToken;
        try {
            eventListener.sendPasswordResetVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/registration/forgot-password-request?success";
    }*/
}

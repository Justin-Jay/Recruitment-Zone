package za.co.RecruitmentZone.controller.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.service.RecruitmentZoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@CrossOrigin("*")
public class RecruitmentZoneWebController {
    private final RecruitmentZoneService recruitmentZoneService;
    private final Logger log = LoggerFactory.getLogger(RecruitmentZoneWebController.class);

    public RecruitmentZoneWebController(RecruitmentZoneService recruitmentZoneService) {
        this.recruitmentZoneService = recruitmentZoneService;
    }

    // Home pages
    @GetMapping("/")
    public String home(Model model) {
       // List<Vacancy> vacancies = recruitmentZoneService.getAllVacancies();
       // log.info("Total Vacancies: " + vacancies.size());
       // model.addAttribute("totalNumberOfVacancies", vacancies.size());
       // model.addAttribute("vacancies", vacancies);
        return "home";
    }

    @GetMapping("/vacancies")
    public String vacancies(Model model) {
        List<Vacancy> allVacancies = new ArrayList<>();
        try {
            allVacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve vacancies, retrieving all vacancies ");
        }
        model.addAttribute("vacancies", allVacancies);
        return "vacancies";
    }

    @GetMapping("/add-vacancy")
    public String showCreateVacancyForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        return "fragments/vacancy/add-vacancy";
    }

    /*    @GetMapping("/manage-vacancies")
        public String manageVacancies(Model model) {
            List<Vacancy> vacancies = new ArrayList<>();
            try {
                vacancies = recruitmentZoneService.getAllVacancies();
            } catch (Exception e) {
                log.info("Exception trying to retrieve employee vacancies, retrieving all active vacancies ");
            }
            model.addAttribute("Vacancies", vacancies);
            return "fragments/vacancy/manage-vacancies";
        }*/
    @PostMapping("/view-vacancy")
    public String showVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy optionalVacancy = recruitmentZoneService.findVacancyById(vacancyID);
        log.info("Looking for {}", vacancyID);
        log.info(optionalVacancy.toString());
        model.addAttribute("vacancy", optionalVacancy);
        return "fragments/vacancy/view-vacancy";
    }
    @PostMapping("/save-vacancy")
    public String saveVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/add-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:vacancies";
    }
    @PostMapping("/update-vacancy")
    public String updateVacancy(@RequestParam("vacancyID") Long vacancyID, Model model) {
        Vacancy vacancy = recruitmentZoneService.findVacancyById(vacancyID);
        model.addAttribute("vacancy", vacancy);
        return "fragments/vacancy/update-vacancy";
    }

    @PostMapping("/save-updated-vacancy")
    public String saveUpdatedVacancy(@Valid @ModelAttribute("vacancy")Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments/vacancy/update-vacancy";
        }
        recruitmentZoneService.saveVacancy(vacancy);
        return "redirect:vacancies";
    }

    @GetMapping("/manageVacancies")
    public String manageVacancies(Model model) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            vacancies = recruitmentZoneService.getAllVacancies();
        } catch (Exception e) {
            log.info("Exception trying to retrieve employee vacancies, retrieving all active vacancies ");
        }
        model.addAttribute("Vacancies", vacancies);
        return "fragments/vacancy/manage-vacancies";
    }

/*    @PostMapping("/add")
    public String addRecruiter(@ModelAttribute Recruiter recruiter) {
        // Process form submission to add a new recruiter
        recruiterService.addRecruiter(recruiter);
        return "redirect:/recruiters";
    }*/

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "fragments/info/about-us";
    }

    @GetMapping("/contactus")
    public String contactus() {
        return "fragments/info/contact-us";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
    /*    List<Blog> blogs = new ArrayList<>();
        try {
            blogs = recruitmentZoneService.getBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs");
        }
        model.addAttribute("blogs", blogs);*/
        return "blog";
    }

    @GetMapping("/addblog")
    public String addBlog(Model model) {
        List<Blog> blogs = new ArrayList<>();
        try {
            blogs = recruitmentZoneService.getBlogs();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs");
        }
        model.addAttribute("blogs", blogs);
        return "blogPages/addblog";
    }



/*
    @GetMapping("/createblog")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "createblog";
    }
    @PostMapping("/createblog")
    public String createBlog(@Valid @ModelAttribute Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createvacancy";
        }
        recruitmentZoneService.createBlog(blog);
        return "redirect:/manageblog";
    }


    @GetMapping("/manageEmployees")
    public String manageEmployees(Model model) {
        List<Employee> employees = null;
        try {
            employees = recruitmentZoneService.getEmployees();
        } catch (Exception e) {
            log.info("Exception trying to retrieve blogs");
        }
        model.addAttribute("employees", employees);
        return "manageEmployees";
    }



    @GetMapping("/applications")
    public String viewApplications(Model model) {
        List<Application> applications = null;
        List<Candidate> candidates = new ArrayList<>();
        try {
            applications = recruitmentZoneService.getApplications();
            applications.forEach(n->{
                candidates.add(n.getCandidate());
            });
        } catch (Exception e) {
            log.info("Exception trying to retrieve applications");
        }
        model.addAttribute("applications", applications);
        model.addAttribute("candidates", candidates);
        return "applications";
    }


    @GetMapping("/EmployeeVacancies")
    public String viewEmployeeVacancies(Model model) {
        Integer Employee_id = 1;
        List<Vacancy> employeeVacancies = null;
        try {
            employeeVacancies = recruitmentZoneService.getEmployeeVacancies(Employee_id);
        } catch (Exception e) {
            log.info("Exception trying to retrieve employee vacancies, retrieving all active vacancies ");
        }

        model.addAttribute("employeeVacancies", employeeVacancies);
        return "EmployeeVacancies";
    }

    @GetMapping("/create")
    public String showCreateVacancyForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        return "createvacancy";
    }
    @PostMapping("/create")
    public String createVacancy(@Valid @ModelAttribute Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createvacancy";
        }
        recruitmentZoneService.createVacancy(vacancy);
        return "redirect:/vacancies";
    }
    @PostMapping("/update-vacancy-status/")
    public String expireVacancy(@Valid @ModelAttribute Integer vacancyID) {
        recruitmentZoneService.setVacancyStatusToExpired(vacancyID);
        return "redirect:/vacancies";
    }
    @GetMapping("/view-recruiter-vacancies")
    public String viewRecruiterVacancies(Model model) {
        List<Vacancy> vacancies = recruitmentZoneService.getActiveVacancies();
        model.addAttribute("vacancies", vacancies);
        return "list-vacancies";
    }
    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }*/
    // my own


    // need a controller method to process the HTML form

/*    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }*/

    // need a controller method to process the HTML form
/*
    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }
*/

/*
    @RequestMapping("/processFormV2")
    public String processFormV2(HttpServletRequest request, Model model) {
        String theName = request.getParameter("recruiterName");
        theName = theName.toUpperCase();
        String result = "YOOOO !" + theName;
        model.addAttribute("recruiterName", result);
        return "helloworld";
    }
*/

  /*  @PostMapping("/addVacancy")
    public String addNewVacancy(@Valid @ModelAttribute("vacancy") Vacancy vacancy) {
        recruitmentZoneService.createVacancy(vacancy);
        return "redirect:/";
    }*/

/*
    @GetMapping("/addVacancy")
    public String addVacancy(Model model) {
        List<Vacancy> vacancies = recruitmentZoneService.findAllVacancies();
        model.addAttribute("vacancies",vacancies);
        return "redirect:/myVacancies";
    }
*/







/*    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        try {
            String storedFileName = fileService.uploadFile(file);
            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName);
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);

        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }*/


    // webView


    // Web-view method to show a vacancy creation form

    // Web-view method to create a new vacancy


    // Web-view method to show a list of vacancies


    // Web-view method to expire a vacancy


    // Folder where uploaded files will be stored
/*
    @PostMapping("/submitApplication")
    public String submitApplication(@Valid @ModelAttribute Application application,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "applicationForm";
        }

        // Additional validations like checking for already submitted application, etc.

        try {
            // Attempt to store the file securely
            String storedFileName = fileService.uploadFile(file);

            if (storedFileName == null) {
                redirectAttributes.addFlashAttribute("message", "File upload failed.");
                return "redirect:/uploadStatus";
            }

            application.setCvFilePath(storedFileName); // Save the stored file name in the application object

            // Other logic to process the application, such as publishing an event
            Gson gson = new Gson();
            String json = gson.toJson(application);
            candidateService.publishCandidateAppliedEvent(json);
        } catch (Exception e) {
            log.info("FAILED TO POST EVENT " + e.getMessage());
        }

        return "redirect:/vacancies";
    }
*/



   /* @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.createNewUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Creating User: ", e);
            return "errorPage";
        }
    }*/

/*    @PostMapping("/createRecruiter")
    public String createRecruiter(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.publishApplicationUserCreateEvent(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Adding Recruiter: ", e);
            return "errorPage";
        }
    }*/

/*    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "errorPage";
        }

        try {
            applicationUserService.updateUser(userDto);
            return "successPage";
        } catch (Exception e) {
            log.error("Error Updating User: ", e);
            return "errorPage";
        }
    }
*/


}

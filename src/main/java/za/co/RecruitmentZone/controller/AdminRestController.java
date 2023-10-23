package za.co.RecruitmentZone.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/rest")
@CrossOrigin("*")
public class AdminRestController {

    private final Logger log = LoggerFactory.getLogger(AdminRestController.class);
    private final ApplicationUserService applicationUserService;

    public AdminRestController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            applicationUserService.createNewRestUser(user);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Creating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDto) {
        try {
            applicationUserService.updateUser(id, userDto);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUserAuthorities/{id}")
    public ResponseEntity<String> updateUserAuthorities(@PathVariable("id") Integer id, @RequestBody AuthoritiesDTO authoritiesDto) {
        try {
            applicationUserService.updateUserAuthorities(id, authoritiesDto.getRoles());
            return new ResponseEntity<>("User Authorities Updated Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Updating User Authorities: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


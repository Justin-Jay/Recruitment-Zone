package za.co.RecruitmentZone.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import za.co.RecruitmentZone.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class GuestController {

    Logger log = LoggerFactory.getLogger(GuestController.class);

    UserService userService;

    public GuestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String helloUserController() {
        return "User access level.";
    }

  /*  @PostMapping("/saveUser")
    public String saveUser() {
        try {
            userService.addUser();
            return "OK";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "NOT OK";
        }
    }*/


/*    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody String json) {
        try {
            userService.process(json);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.info("Unable to save");
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }*/

/*    @GetMapping("/getUser{userID}")
    public ResponseEntity<User_> getUser(@PathVariable Long userID) {
        try {
            User_ userFound = userService.findUserByID(userID);
            if (userFound != null) {
                return new ResponseEntity<>(userFound, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.info("Unable to save");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/


    // Login
    // View Profile
    // Apply for vacancy
}

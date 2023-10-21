package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.Entity.ApplicationUser;
import za.co.RecruitmentZone.Entity.Vacancy;
import za.co.RecruitmentZone.repository.UserRepository;
import za.co.RecruitmentZone.repository.VacancyRepository;

import java.util.Optional;

@Service
public class RecruiterEventService {
    Logger log = LoggerFactory.getLogger(RecruiterEventService.class);

    VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    public RecruiterEventService(VacancyRepository vacancyRepository,
                                 UserRepository userRepository) {
        this.vacancyRepository = vacancyRepository;
        this.userRepository = userRepository;
    }

    // event publisher
    public String activateVacancy(Integer vacancyID) {

        try {
            Vacancy vacancy = new Vacancy(vacancyID);

            eventPoster.activateVacancy(vacancy);
        } catch (Exception e) {
            log.info(e.getMessage());
            return "Unable to find vacancy";
        }


    }

    // event listener
    public boolean activate(Vacancy vacancy) {
        // activate vacancy
        return true;
    }


    public String addVacancy(String json) {

        try {
            Gson gson = new Gson();

            // Deserialize the JSON string into a MyObject object
            Vacancy newVacancy = gson.fromJson(json, Vacancy.class);


            System.out.println("Vacancy Received: " + newVacancy.toString());


            // Your processing logic here

            eventPoster.newVacancy(newVacancy);
            return "Vacancy Event Posted";
        } catch (Exception e) {
            log.info("Unable to post event");
            return "Failed to create event";
        }
    }


    public String suspendVacancy(Integer vacancyID) {

        try {


            // Deserialize the JSON string into a MyObject object
            Optional<Vacancy> vacancyToSuspend = vacancyRepository.findById(vacancyID);


            System.out.println("Vacancy Received: " + vacancyToSuspend.toString());


            if (vacancyToSuspend.isPresent()) {
                eventPoster.suspendVacancy(vacancyToSuspend);
            }
            return "Vacancy has been suspended";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "failed to suspend vacancy";
        }

    }

    public String amendVacancy(String json) {

        try {
            Gson gson = new Gson();

            // Deserialize the JSON string into a MyObject object
            Vacancy vacancyToAmend = gson.fromJson(json, Vacancy.class);

            // Your processing logic here
            eventPoster.amendVacancy(vacancyToAmend);
            return "Vacancy Has Been Amended";
        } catch (Exception e) {
            return "Unable to Amend Vacancy";
        }
    }

    public String rejectCandidate(Integer candidateID){
        try {

            Optional<Vacancy> rejectedCandidate = vacancyRepository.findById(candidateID);

            System.out.println("Vacancy Received: " + rejectedCandidate.toString());

            if (rejectedCandidate.isPresent()) {
                eventPoster.rejectCandidate(rejectedCandidate);
            }
            return "Candidate Rejected";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "failed reject Candidate";
        }
    }


    public String shortList(Integer userID,Integer vacancyID){
        try {
            Optional<ApplicationUser> candidate = userRepository.findById(userID);
            Optional<Vacancy> vacancy = vacancyRepository.findById(vacancyID);


            System.out.println("Candidate Received: " + candidate.toString());
            System.out.println("Vacancy Received: " + vacancy.toString());

            if (candidate.isPresent() && vacancy.isPresent()) {
                int[] array = {userID,vacancyID};
                eventPoster.shortList(array);
            }
            return "Candidate Rejected";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "failed reject Candidate";
        }
    }
    public String removeFromShortList(Integer userID,Integer vacancyID){
        try {
            Optional<ApplicationUser> candidate = userRepository.findById(userID);
            Optional<Vacancy> vacancy = vacancyRepository.findById(vacancyID);


            System.out.println("Candidate Received: " + candidate.toString());
            System.out.println("Vacancy Received: " + vacancy.toString());

            if (candidate.isPresent() && vacancy.isPresent()) {
                int[] array = {userID,vacancyID};
                eventPoster.removeFromShortList(array);
            }
            return "Candidate Rejected";
        } catch (Exception e) {
            log.info(e.getMessage());
            return "failed reject Candidate";
        }
    }



}

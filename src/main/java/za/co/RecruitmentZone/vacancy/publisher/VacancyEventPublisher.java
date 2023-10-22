package za.co.RecruitmentZone.vacancy.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.admin.controller.AdminController;
import za.co.RecruitmentZone.vacancy.events.VacancyAmendedEvent;
import za.co.RecruitmentZone.vacancy.events.VacancyCreateEvent;
import za.co.RecruitmentZone.vacancy.events.VacancyActivatedEvent;
import za.co.RecruitmentZone.vacancy.util.Vacancy;

import java.time.Clock;

@Service
public class VacancyEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    Logger log = LoggerFactory.getLogger(AdminController.class);

    public VacancyEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    public boolean publishVacancyCreateEvent(Vacancy vacancy) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            VacancyCreateEvent event = new VacancyCreateEvent(vacancy,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }

    public boolean publishVacancyAmendedEvent(Vacancy vacancy) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            VacancyAmendedEvent event = new VacancyAmendedEvent(vacancy,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }

    //publishVacancyActivatedEvent
    public boolean publishVacancyActivatedEvent(Integer vacancyID) {
        try {
            VacancyActivatedEvent event = new VacancyActivatedEvent(vacancyID);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }

    public boolean publishVacancySuspendedEvent(Integer vacancyID) {
        try {
            VacancySuspendedEvent event = new VacancySuspendedEvent(vacancyID);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }

/*
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

*/

/*    public String rejectCandidate(Integer candidateID){
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
    }*/

}

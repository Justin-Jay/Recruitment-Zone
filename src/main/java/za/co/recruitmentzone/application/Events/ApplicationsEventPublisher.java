package za.co.recruitmentzone.application.Events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.application.dto.NewApplicationDTO;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.Events.FileUploadEvent;
import za.co.recruitmentzone.candidate.Events.SaveSubmissionEvent;

@Component
public class ApplicationsEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(ApplicationsEventPublisher.class);

    public ApplicationsEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public boolean publishSaveSubmissionEvent(Candidate candidate,Long vacancyID) {
        SaveSubmissionEvent saveSubmissionEvent = new SaveSubmissionEvent(candidate,vacancyID);
        log.info("Executing publishWebsiteQueryReceivedEvent");
        try {
            eventPublisher.publishEvent(saveSubmissionEvent);
            log.info("EVENT  publishWebsiteQueryReceivedEvent POSTED");
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }

    }

    public boolean publishFileUploadEvent(Long candidateID, NewApplicationDTO newApplicationDTO) {
        FileUploadEvent fileUploadEvent = new FileUploadEvent(newApplicationDTO.getCvFile(),candidateID,newApplicationDTO.getVacancyID());
        log.info("Executing publishFileUploadedEvent");
        try {
            eventPublisher.publishEvent(fileUploadEvent);
            log.info("EVENT publishFileUploadedEvent POSTED");
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }

    }


   /* public boolean publishApplicationSubmittedEvent(CandidateApplicationDTO candidateApplication) {
        try {
            ApplicationSubmittedEvent event = new ApplicationSubmittedEvent(candidateApplication);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("ApplicationSubmittedEvent Posted..");
            return false;
        }
    }*/

 /*   public boolean publishVacancyAmendedEvent(Vacancy vacancy) {
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
*/
/*    public boolean publishVacancyActivatedEvent(Integer vacancyID) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            VacancyActivatedEvent event = new VacancyActivatedEvent(vacancyID,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }*/

/*    public boolean publishVacancyExpiredEvent(Integer vacancyID) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            VacancyExpiredEvent event = new VacancyExpiredEvent(vacancyID,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }
    }*/

//    publishVacancyActivatedEvent
          //  publishVacancyExpiredEvent

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
    }*/


/*   public String shortList(Integer userID,Integer vacancyID){
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
    }*/

/*    public String removeFromShortList(Integer userID,Integer vacancyID){
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

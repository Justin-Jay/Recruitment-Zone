/*
package za.co.RecruitmentZone.events.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Application;
import za.co.RecruitmentZone.entity.domain.Candidate;
import za.co.RecruitmentZone.entity.domain.Vacancy;
import za.co.RecruitmentZone.events.EventStore.Candidate.CandidateAppliedEvent;
import za.co.RecruitmentZone.events.EventStore.Vacancy.VacancyCreateEvent;

import java.time.Clock;

@Service
public class CandidateEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    private final Logger log = LoggerFactory.getLogger(CandidateEventPublisher.class);

    public CandidateEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    public boolean publishVacancyCreateEvent( Vacancy vacancy) {
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

    public boolean publishCandidateAppliedEvent(Application application) {
        try {
            Clock baseClock = Clock.systemDefaultZone();
            CandidateAppliedEvent event = new CandidateAppliedEvent(application,baseClock);
            eventPublisher.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.info("Unable to post event");
            return false;
        }

    }




*/
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

*//*


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
    }*//*


}
*/

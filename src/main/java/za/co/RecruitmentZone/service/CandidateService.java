package za.co.RecruitmentZone.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.publisher.CandidateEventPublisher;

@Service
public class CandidateService {

    private final CandidateEventPublisher candidateEventPublisher;

    private final Logger log = LoggerFactory.getLogger(CandidateService.class);

    // Service layer takes in String json objects from controller and converts to POJO before passing to vacancyEventPublisher
    // any repository methods are done here.

    public CandidateService(CandidateEventPublisher candidateEventPublisher) {
        this.candidateEventPublisher = candidateEventPublisher;
    }


    // repository methods



    // json conversion methods - create events

    public boolean publishCandidateAppliedEvent(String json){
        Gson gson = new Gson();
        Application newApplication = gson.fromJson(json, Application.class);
        log.info("User Saved");
        return candidateEventPublisher.publishCandidateAppliedEvent(newApplication);
    }





}

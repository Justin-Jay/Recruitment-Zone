package za.co.RecruitmentZone.service.EventOrchestration;

import com.google.gson.Gson;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.RecruitmentZone.entity.Application;
import za.co.RecruitmentZone.events.Candidate.CandidateAppliedEvent;
import za.co.RecruitmentZone.publisher.CandidateEventPublisher;
import za.co.RecruitmentZone.repository.CandidateRepository;
import za.co.RecruitmentZone.entity.Candidate;
import za.co.RecruitmentZone.service.FileService;
import za.co.RecruitmentZone.storage.GoogleCloudStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Clock;

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

package za.co.recruitmentzone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.co.recruitmentzone.service.RecruitmentZoneService;

@SpringBootApplication
public class RecruitmentZoneApplication {
    private static final Logger log = LoggerFactory.getLogger(RecruitmentZoneService.class);
    public static void main(String[] args) {
        log.info(" RecruitmentZoneApplication started ");
        SpringApplication.run(RecruitmentZoneApplication.class, args);
    }

}

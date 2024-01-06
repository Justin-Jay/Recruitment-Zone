package za.co.recruitmentzone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecruitmentZoneApplication {
    private static final Logger log = LoggerFactory.getLogger(RecruitmentZoneApplication.class);
    public static void main(String[] args) {
        log.info(" RecruitmentZoneApplication started ");
        SpringApplication.run(RecruitmentZoneApplication.class, args);
    }

}

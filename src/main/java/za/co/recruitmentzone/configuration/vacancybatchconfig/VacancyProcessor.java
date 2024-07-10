package za.co.recruitmentzone.configuration.vacancybatchconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import za.co.recruitmentzone.client.entity.Client;
import za.co.recruitmentzone.client.service.ClientService;
import za.co.recruitmentzone.employee.entity.Employee;
import za.co.recruitmentzone.employee.service.EmployeeService;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.time.LocalDate;

@Component
public class VacancyProcessor implements ItemProcessor<Vacancy, Vacancy> {
    private final Logger log = LoggerFactory.getLogger(VacancyProcessor.class);

/*    private final ClientService clientService;
    private final EmployeeService employeeService;

    public VacancyProcessor(ClientService clientService, EmployeeService employeeService) {
        this.clientService = clientService;
        this.employeeService = employeeService;
    }*/

    @Override
    public Vacancy process(Vacancy vacancy) {
        log.info("<--- Vacancy Batch process --->");
        log.info("vacancy in flight \n {}", vacancy.printVacancy());
        //Client client = clientService.findClientByID(vacancy.getTheClientID());
        //Employee e = employeeService.getEmployeeByid(vacancy.getTheEmpID());
        //vacancy.setClient(client);
        //vacancy.setEmployee(e);

        LocalDate today = LocalDate.now();
        LocalDate vacancyPublishDate = vacancy.getPublish_date();
        LocalDate vacancyExpirationDate = vacancy.getEnd_date();

        //  check publish date, if publish date is before today, or equal to today, should be active
        if (vacancy.getStatus() == VacancyStatus.PENDING) {
            log.info("vacancy status is PENDING");

            if (vacancyPublishDate.isBefore(today) || vacancyPublishDate.isEqual(today)) {
                vacancy.setStatus(VacancyStatus.ACTIVE);
            }
        }

        //  check expiration date, if expiration date is before today, should be expired
        else if (vacancy.getStatus() == VacancyStatus.ACTIVE) {
            log.info("vacancy status is ACTIVE");
            if (vacancyExpirationDate.isBefore(today)) {
                vacancy.setStatus(VacancyStatus.EXPIRED);
            }
        }
        else {
            log.info("vacancy status is EXPIRED... skipping");
        }

        return vacancy;
    }
}

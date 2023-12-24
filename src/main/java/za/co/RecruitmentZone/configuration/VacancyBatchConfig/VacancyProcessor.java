package za.co.RecruitmentZone.configuration.VacancyBatchConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.RecruitmentZone.client.entity.Client;
import za.co.RecruitmentZone.client.service.ClientService;
import za.co.RecruitmentZone.employee.entity.Employee;
import za.co.RecruitmentZone.employee.service.EmployeeService;
import za.co.RecruitmentZone.util.Enums.VacancyStatus;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;

import java.time.LocalDate;

public class VacancyProcessor implements ItemProcessor<Vacancy, Vacancy> {
    private final Logger log = LoggerFactory.getLogger(VacancyProcessor.class);

    private final ClientService clientService;
    private final EmployeeService employeeService;

    public VacancyProcessor(ClientService clientService, EmployeeService employeeService) {
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    @Override
    public Vacancy process(Vacancy vacancy) {
        Client client = clientService.findClientByID(vacancy.getTheClientID());
        Employee e = employeeService.findEmployeeByID(vacancy.getTheEmpID());
        vacancy.setClient(client);
        vacancy.setEmployee(e);
        log.info("PROCESS Batch START");
        log.info("<-----Vacancy Status Updater---->");
        LocalDate currentDate = LocalDate.now();

        LocalDate vacancyPublishDate = vacancy.getPublish_date();
        LocalDate vacancyExpirationDate = vacancy.getEnd_date();
        log.info("vacancy in flight \n {}", vacancy);

        if (vacancyPublishDate.isBefore(currentDate) && vacancy.getStatus() != VacancyStatus.EXPIRED) {
            if (vacancy.getStatus() != VacancyStatus.ACTIVE) {
                vacancy.setStatus(VacancyStatus.ACTIVE);
            }
        }

        if (vacancyExpirationDate.isBefore(currentDate) && vacancy.getStatus() != VacancyStatus.PENDING) {
            if (vacancy.getStatus() != VacancyStatus.EXPIRED) {
                vacancy.setStatus(VacancyStatus.EXPIRED);
            }
        }

        if (vacancy.getStatus() == VacancyStatus.PENDING && vacancyPublishDate.isBefore(currentDate)) {
            vacancy.setStatus(VacancyStatus.ACTIVE);
        }

        if (vacancy.getStatus() == VacancyStatus.EXPIRED && vacancyPublishDate.isBefore(currentDate) && !vacancyExpirationDate.isBefore(currentDate)) {
            vacancy.setStatus(VacancyStatus.ACTIVE);
        }

        return vacancy;
    }
}

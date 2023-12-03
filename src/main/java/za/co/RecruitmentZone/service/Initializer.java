/*
package za.co.RecruitmentZone.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.entity.Enums.ApplicationStatus;
import za.co.RecruitmentZone.entity.Enums.BlogStatus;
import za.co.RecruitmentZone.entity.Enums.EducationLevel;
import za.co.RecruitmentZone.entity.Enums.VacancyStatus;
import za.co.RecruitmentZone.entity.domain.*;
import za.co.RecruitmentZone.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Transactional
public class Initializer {

    private final Logger log = LoggerFactory.getLogger(Initializer.class);
    private final ApplicationRepository applicationRepository;
    private final BlogRepository blogRepository;
    private final EmployeeRepository employeeRepsitory;
    private final CandidateRepository candidateRepsotiory;
    private final ClientRepository clientRepository;
    private final ContactPeopleRepository contactPeopleRepository;
    private final VacancyRepository vacancyRepository;

    private final LocationsRepository locationsRepository;
    private final ClientLocationRepository clientLocationRepository;
    private  final EmployeeBlogRepository employeeBlogRepository;
    public Initializer(ApplicationRepository applicationRepository, BlogRepository blogRepository, EmployeeRepository employeeRepsitory, CandidateRepository candidateRepsotiory, ClientRepository clientRepository, ContactPeopleRepository contactPeopleRepository, VacancyRepository vacancyRepository, LocationsRepository locationsRepository, ClientLocationRepository clientLocationRepository, EmployeeBlogRepository employeeBlogRepository) {
        this.applicationRepository = applicationRepository;
        this.blogRepository = blogRepository;
        this.employeeRepsitory = employeeRepsitory;
        this.candidateRepsotiory = candidateRepsotiory;
        this.clientRepository = clientRepository;
        this.contactPeopleRepository = contactPeopleRepository;
        this.vacancyRepository = vacancyRepository;
        this.locationsRepository = locationsRepository;
        this.clientLocationRepository = clientLocationRepository;
        this.employeeBlogRepository = employeeBlogRepository;
    }


    @EventListener
    public void onApplicationStartUp(ApplicationStartedEvent event) {

        // Application
        List<Application> applications = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Application application = new Application();
            application.setDate_received(generateRandomDate());
            application.setSubmission_date(generateRandomDate());
            application.setStatus(ApplicationStatus.PENDING);  // You can choose the appropriate status
            applications.add(application);
        }
        applicationRepository.saveAll(applications);
// Blog
        List<Blog> blogs = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Blog blog = new Blog(
                    "Blog Title " + i,
                    "Blog Description " + i,
                    "Body Content " + i,
                    generateRandomDate(),
                    generateRandomDate()
            );
            blog.setStatus(BlogStatus.PENDING);  // You can choose the appropriate status
            blogs.add(blog);
        }

        blogRepository.saveAll(blogs);
// Candidate
        List<Candidate> candidates = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Candidate candidate = new Candidate(
                    "FirstName" + i,
                    "LastName" + i,
                    "IDNumber" + i,
                    "email" + i + "@example.com",
                    "123456789" + i,
                    "Province" + i,
                    "Role" + i,
                    "Employer" + i,
                    "Seniority" + i,
                    EducationLevel.BACHELORS_DEGREE,  // You can choose the appropriate education level
                    true
            );
            candidates.add(candidate);
        }
        candidateRepsotiory.saveAll(candidates);
// Client
        List<Client> clients = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Client client = new Client("Client" + i, "Industry" + i);
            clients.add(client);
        }
        clientRepository.saveAll(clients);
// ContactPerson
        List<ContactPerson> contactPeople = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ContactPerson contactPerson = new ContactPerson(
                    "ContactFirstName" + i,
                    "ContactLastName" + i,
                    "contact" + i + "@example.com",
                    "123456" + i,
                    "987654" + i
            );
            contactPeople.add(contactPerson);
        }

        contactPeopleRepository.saveAll(contactPeople);
// Employee
        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Employee employee = new Employee(
                    "Username" + i,
                    "FirstName" + i,
                    "LastName" + i,
                    "email" + i + "@example.com",
                    "9876543210"
            );
            employees.add(employee);
        }

        employeeRepsitory.saveAll(employees);
// Vacancy
        List<Vacancy> vacancies = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Vacancy vacancy = new Vacancy(
                    "JobTitle" + i,
                    "JobDescription" + i,
                    "Seniority" + i,
                    "Requirements" + i,
                    "Location" + i,
                    "Industry" + i,
                    "2023-12-01",
                    "2023-12-10",
                    VacancyStatus.ACTIVE,  // You can choose the appropriate status
                    "JobType" + i,
                    "EmpType" + i
            );
            vacancies.add(vacancy);
        }

        vacancyRepository.saveAll(vacancies);

        insertData();

        log.info("-----DONE-----");
    }

    // Function to generate a random date within the 12-month period
    private String generateRandomDate() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);

        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay).toString();
    }


    public void insertData() {
        // Insert data into employee_blog table
        insertEmployeeBlogData();

        // Insert data into candidate_application table
        insertCandidateApplicationData();

        // Insert data into client_contact_person table
        insertClientContactPersonData();

        // Insert data into client_vacancy table
        insertClientVacancyData();

        // Insert data into employee_vacancy table
        insertEmployeeVacancyData();

        // Insert data into client_location table
        insertClientLocationData();
    }

    private void insertEmployeeBlogData() {
        List<Employee> employees = employeeRepsitory.findAll();
        List<Blog> blogs = blogRepository.findAll();

        for (Employee employee : employees) {
            for (Blog blog : blogs) {
                EmployeeBlog employeeBlog = new EmployeeBlog(employee.getEmployeeID(), blog.getBlogID());
                employeeBlogRepository.save(employeeBlog);
            }
        }
    }

    private void insertCandidateApplicationData() {
        List<Candidate> candidates = candidateRepsotiory.findAll();
        List<Application> applications = applicationRepository.findAll();

        for (Candidate candidate : candidates) {
            for (Application application : applications) {
                CandidateApplication candidateApplication = new CandidateApplication(candidate.getCandidateID(), application.getApplicationID());
                candidateApplicationRepository.save(candidateApplication);
            }
        }
    }

    private void insertClientContactPersonData() {
        List<Client> clients = clientRepository.findAll();
        List<ContactPerson> contactPeople = contactPeopleRepository.findAll();

        for (Client client : clients) {
            for (ContactPerson contactPerson : contactPeople) {
                ClientContactPerson clientContactPerson = new ClientContactPerson(client.getClientID(), contactPerson.getContactPersonID());
                clientContactPersonRepository.save(clientContactPerson);
            }
        }
    }

    private void insertClientVacancyData() {
        List<Client> clients = clientRepository.findAll();
        List<Vacancy> vacancies = vacancyRepository.findAll();

        for (Client client : clients) {
            for (Vacancy vacancy : vacancies) {
                ClientVacancy clientVacancy = new ClientVacancy(client.getClientID(), vacancy.getVacancyID());
                clientVacancyRepository.save(clientVacancy);
            }
        }
    }

    private void insertEmployeeVacancyData() {
        List<Employee> employees = employeeRepsitory.findAll();
        List<Vacancy> vacancies = vacancyRepository.findAll();

        for (Employee employee : employees) {
            for (Vacancy vacancy : vacancies) {
                EmployeeVacancy employeeVacancy = new EmployeeVacancy(employee.getEmployeeID(), vacancy.getVacancyID());
                employeeVacancyRepository.save(employeeVacancy);
            }
        }
    }

    private void insertClientLocationData() {
        List<Client> clients = clientRepository.findAll();
        List<Locations> locations = locationsRepository.findAll();

        for (Client client : clients) {
            for (Locations location : locations) {
                ClientLocation clientLocation = new ClientLocation(client.getClientID(), location.getLocationID());
                clientLocationRepository.save(clientLocation);
            }
        }
    }
}
}




*/

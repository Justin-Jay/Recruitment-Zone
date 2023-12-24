package za.co.RecruitmentZone.configuration.VacancyBatchConfig;


import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import za.co.RecruitmentZone.client.service.ClientService;
import za.co.RecruitmentZone.employee.service.EmployeeService;
import za.co.RecruitmentZone.vacancy.entity.Vacancy;
import za.co.RecruitmentZone.vacancy.repository.VacancyRepository;



@Configuration
public class VacancyBatchConfig {
    private final JobRepository jobRepository;
    private final VacancyRepository vacancyRepository;
     private final PlatformTransactionManager platformTransactionManager;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final DataSource dataSource;
    private final Logger log = LoggerFactory.getLogger(VacancyBatchConfig.class);

    public VacancyBatchConfig(JobRepository jobRepository , VacancyRepository vacancyRepository,
                              PlatformTransactionManager platformTransactionManager, ClientService clientService, EmployeeService employeeService, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.vacancyRepository = vacancyRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.dataSource = dataSource;
    }

  /*  @Bean
    public ItemReader<Vacancy> reader() {
        log.info("READER Batch STARTED");

        int maxId = 50;

        RepositoryItemReader<Vacancy> reader = new RepositoryItemReader<>();
        reader.setRepository(vacancyRepository);

        log.info("Configuring RepositoryItemReader...");
        reader.setMethodName("findAllVacancies");
        reader.setArguments(Collections.singletonList(maxId));

        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("vacancyID", Sort.Direction.ASC);
        reader.setSort(sorts);
        reader.setPageSize(10);
        log.info("RepositoryItemReader configured: {}", reader.toString());
        return reader;
    }*/

    @Bean
    public JdbcCursorItemReader<Vacancy> reader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Vacancy>()
                .dataSource(dataSource)
                .name("vacancy")
                .sql("select * from vacancy")
                .rowMapper(new VacancyRowMapper())
                .build();

    }

    @Bean
    public VacancyProcessor vacancyProcessor() {
        return new VacancyProcessor(clientService,employeeService);
    }

    @Bean
    public VacancyRowMapper vacancyRowMapper() {
        return new VacancyRowMapper();
    }


    @Bean
    public RepositoryItemWriter<Vacancy> writer() {
        log.info("WRITER Batch STARTED");
        RepositoryItemWriter<Vacancy> writer = new RepositoryItemWriter<>();
        writer.setRepository(vacancyRepository);
        writer.setMethodName("save");
        log.info("WRITER Batch END");
        return writer;
    }


    @Bean
    public Step step1(
            JobRepository jobRepository) {
        return new StepBuilder("vacancyStatusStep", jobRepository)
                .<Vacancy, Vacancy>chunk(100,platformTransactionManager)
                .reader(reader(dataSource))
                .processor(vacancyProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job vacancyJob() {
        return new JobBuilder("vacancyJob", jobRepository)
                .start(step1(jobRepository))
                .build();

    }


}

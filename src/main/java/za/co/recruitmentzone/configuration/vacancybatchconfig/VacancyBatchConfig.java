package za.co.recruitmentzone.configuration.vacancybatchconfig;


import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import za.co.recruitmentzone.client.service.ClientService;
import za.co.recruitmentzone.employee.service.EmployeeService;
import za.co.recruitmentzone.vacancy.entity.Vacancy;
import za.co.recruitmentzone.vacancy.repository.VacancyRepository;



@Configuration
@EnableBatchProcessing
public class VacancyBatchConfig {
    private final JobRepository jobRepository;
    private final VacancyRepository vacancyRepository;
     private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;
    private final Logger log = LoggerFactory.getLogger(VacancyBatchConfig.class);

    public VacancyBatchConfig(JobRepository jobRepository , VacancyRepository vacancyRepository,
                              PlatformTransactionManager platformTransactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.vacancyRepository = vacancyRepository;
        this.platformTransactionManager = platformTransactionManager;
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
                .name("VACANCY")
                .sql("SELECT * FROM VACANCY")
                .rowMapper(new VacancyRowMapper())
                .build();

    }

    @Bean
    public VacancyProcessor vacancyProcessor() {
//        return new VacancyProcessor(clientService,employeeService);
        return new VacancyProcessor();
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

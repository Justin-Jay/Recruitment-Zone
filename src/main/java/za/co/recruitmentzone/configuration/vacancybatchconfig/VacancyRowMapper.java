package za.co.recruitmentzone.configuration.vacancybatchconfig;

import org.springframework.jdbc.core.RowMapper;
import za.co.recruitmentzone.util.enums.EmpType;
import za.co.recruitmentzone.util.enums.Industry;
import za.co.recruitmentzone.util.enums.JobType;
import za.co.recruitmentzone.util.enums.VacancyStatus;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VacancyRowMapper implements RowMapper<Vacancy> {

    public static final String vacancyID = "vacancyID";
    public static final String job_title = "job_title";
    private static final String job_description = "job_description";
    private static final String seniority_level = "seniority_level";
    private static final String requirements = "requirements";
    private static final String category = "category";
    private static final String location = "location";
    private static final String industry = "industry";
    private static final String publish_date = "publish_date";
    private static final String end_date = "end_date";
    private static final String status = "status";
    private static final String jobType = "job_Type";
    private static final String empType = "emp_Type";
    private static final String CREATED_D_TIME = "created";
    private static final String clientID = "clientID";
    private static final String employeeID = "employeeID";

    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {

        Vacancy vacancy = new Vacancy();
        vacancy.setVacancyID(rs.getLong(vacancyID));
        vacancy.setJob_title(rs.getString(job_title));
        vacancy.setJob_description(rs.getString(job_description));
        vacancy.setSeniority_level(rs.getString(seniority_level));
        vacancy.setRequirements(rs.getString(requirements));
        vacancy.setCategory(rs.getString(category));
        vacancy.setLocation(rs.getString(location));
        vacancy.setIndustry(Industry.valueOf(rs.getString(industry)) );
        vacancy.setPublish_date(LocalDate.parse(rs.getString(publish_date)));
        vacancy.setEnd_date(LocalDate.parse(rs.getString(end_date)));
        vacancy.setStatus(VacancyStatus.valueOf(rs.getString(status)) );
        vacancy.setJobType(JobType.valueOf( rs.getString(jobType)));
        vacancy.setEmpType(EmpType.valueOf( rs.getString(empType)));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime created = LocalDateTime.parse(rs.getString(CREATED_D_TIME), formatter);
        vacancy.setCreated(created);
/*
        vacancy.setClient(rs.getObject(clientID, Client.class));
        vacancy.setEmployee(rs.getObject(employeeID, Employee.class));*/

        Long theClientID = rs.getLong(clientID);
        Long theEmployeeID = rs.getLong(employeeID);
        vacancy.setTheClientID(theClientID);
        vacancy.setTheEmpID(theEmployeeID);
        return vacancy;
    }
}

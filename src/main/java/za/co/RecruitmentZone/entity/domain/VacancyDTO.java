package za.co.RecruitmentZone.entity.domain;

public class VacancyDTO {
    private Long vacancyID;
    private String jobTitle;
    private String jobDescription;
    private String seniority_level;
    private String category;
    private String requirements;
    private String location;
    private String industry;
    private String publish_date;
    private String end_date;
    private String jobType;
    private String status;
    private String empType;
    private Long employeeID;

    public VacancyDTO() {
    }

    public VacancyDTO(Long vacancyID, String jobTitle, String jobDescription, String seniority_level, String category, String requirements, String location, String industry, String publish_date, String end_date, String jobType, String status, String empType,Long employeeID) {
        this.vacancyID = vacancyID;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.seniority_level = seniority_level;
        this.category = category;
        this.requirements = requirements;
        this.location = location;
        this.industry = industry;
        this.publish_date = publish_date;
        this.end_date = end_date;
        this.jobType = jobType;
        this.status = status;
        this.empType = empType;
        this.employeeID=employeeID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVacancyID() {
        return vacancyID;
    }

    public void setVacancyID(Long vacancyID) {
        this.vacancyID = vacancyID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    @Override
    public String toString() {
        return "VacancyDTO{" +
                "jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", seniority_level='" + seniority_level + '\'' +
                ", category='" + category + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", jobType='" + jobType + '\'' +
                ", empType='" + empType + '\'' +
                '}';
    }
}

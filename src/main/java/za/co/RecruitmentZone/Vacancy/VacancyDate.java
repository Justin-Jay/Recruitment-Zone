package za.co.RecruitmentZone.Vacancy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VacancyDate {
    private final LocalDateTime date;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public VacancyDate(String dateStr) {
        // Parse the input date string using the custom format
        this.date = LocalDateTime.parse(dateStr, DATE_FORMATTER);
    }

    public String getFormattedDate() {
        // Format the date as a string in the custom format
        return date.format(DATE_FORMATTER);
    }

    public LocalDateTime getDate() {
        return date;
    }
    public boolean isBefore(LocalDateTime otherDate) {
        return this.date.isBefore(otherDate);
    }

    // Add any additional methods or validation as needed
}


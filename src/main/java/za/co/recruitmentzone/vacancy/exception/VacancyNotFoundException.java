package za.co.recruitmentzone.vacancy.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class VacancyNotFoundException extends VacancyException {

    public VacancyNotFoundException(String failureReason) {
        super(failureReason);
    }


}

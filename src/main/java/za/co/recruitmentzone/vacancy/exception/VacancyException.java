package za.co.recruitmentzone.vacancy.exception;

import za.co.recruitmentzone.exception.RzoneException;

public class VacancyException extends RzoneException {

    public VacancyException(String failureReason) {
        super(failureReason);
    }


}

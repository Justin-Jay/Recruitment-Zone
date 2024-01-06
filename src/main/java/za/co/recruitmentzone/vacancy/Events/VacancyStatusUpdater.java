package za.co.recruitmentzone.vacancy.Events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class VacancyStatusUpdater extends ApplicationEvent {
  private final LocalDateTime TIME_NOW = LocalDateTime.now();

    public VacancyStatusUpdater(Object source) {
        super(source);
    }

    public LocalDateTime getTIME_NOW() {
        return TIME_NOW;
    }
}


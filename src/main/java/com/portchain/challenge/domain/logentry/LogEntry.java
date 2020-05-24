package com.portchain.challenge.domain.logentry;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class LogEntry {
    private UpdatedField updatedField;
    private ZonedDateTime arrival;
    private ZonedDateTime departure;
    private boolean isOmitted;
    private ZonedDateTime createdDate;

    public int daysBetweenCreatedDateAnd(ZonedDateTime date) {
        return (int) ChronoUnit.DAYS.between(createdDate, date);
    }
}

package com.portchain.challenge.domain.portcall;

import com.portchain.challenge.domain.logentry.LogEntry;
import com.portchain.challenge.domain.port.Port;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

@Getter
@Setter
@NoArgsConstructor
public class PortCall {
    private ZonedDateTime arrival;
    private ZonedDateTime departure;
    private ZonedDateTime createdDate;
    private boolean isOmitted = false;
    private String service;
    private Port port;
    private List<LogEntry> logEntries = new ArrayList<>();

    public PortCall(ZonedDateTime arrival, ZonedDateTime departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    public int getDuration() {
        return dateDifferenceInMinutes(arrival, departure);
    }

    public Integer getDelay(int delayedDays) {
        LogEntry foreCastLogEntry = null;
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getCreatedDate().isBefore(arrival)
                    && dateDifferenceInDays(logEntry.getCreatedDate(), arrival) >= delayedDays
                    && logEntry.getArrival() != null) {

                if (foreCastLogEntry == null
                        || dateDifferenceInMinutes(foreCastLogEntry.getCreatedDate(), arrival) > dateDifferenceInMinutes(logEntry.getCreatedDate(), arrival))
                foreCastLogEntry = logEntry;
            }
        }

        if (foreCastLogEntry == null) {
            return null;
        }

        return dateDifferenceInMinutes(arrival, foreCastLogEntry.getArrival());
    }

    private int dateDifferenceInMinutes(ZonedDateTime date1, ZonedDateTime date2) {
        return (int) abs(ChronoUnit.MINUTES.between(date1, date2));
    }

    private int dateDifferenceInDays(ZonedDateTime date1, ZonedDateTime date2) {
        return (int) abs(ChronoUnit.DAYS.between(date1, date2));
    }

    @Override
    public String toString() {
        return "Arrival: " + arrival.toLocalDateTime().toString() + ". Departure: " + departure.toLocalDateTime().toString();
    }
}

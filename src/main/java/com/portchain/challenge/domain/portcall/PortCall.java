package com.portchain.challenge.domain.portcall;

import com.portchain.challenge.domain.logentry.LogEntry;
import com.portchain.challenge.domain.logentry.UpdatedField;
import com.portchain.challenge.domain.port.Port;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        if (arrival != null && departure != null) {
            return differenceInMinutes(arrival, departure);
        }
        return 0;
    }

    public Integer getDelay(int delayedDays) {
        Optional<LogEntry> foreCastLogEntry = logEntries
                .stream()
                .filter(logEntry -> logEntry.getUpdatedField().equals(UpdatedField.ARRIVAL)
                        && logEntry.getCreatedDate().isBefore(arrival)
                        && logEntry.getArrival() != null
                        && differenceInDays(logEntry.getCreatedDate(), arrival) >= delayedDays)
                .min(Comparator.comparing(logEntry -> differenceInMinutes(logEntry.getCreatedDate(), arrival)));

        if (foreCastLogEntry.isPresent()) {
            return differenceInMinutes(arrival, foreCastLogEntry.get().getArrival());
        }

        return null;
    }

    private int differenceInMinutes(ZonedDateTime date1, ZonedDateTime date2) {
        return (int) abs(ChronoUnit.MINUTES.between(date1, date2));
    }

    private int differenceInDays(ZonedDateTime date1, ZonedDateTime date2) {
        return (int) abs(ChronoUnit.DAYS.between(date1, date2));
    }

    @Override
    public String toString() {
        return "Arrival: " + arrival.toLocalDateTime().toString() + ". Departure: " + departure.toLocalDateTime().toString();
    }
}

package com.portchain.challenge.format;

import com.portchain.challenge.domain.task.percentiles.Percentile;
import org.springframework.format.Formatter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;

public class PercentileFormatter implements Formatter<Percentile> {

    @Override
    public Percentile parse(String text, Locale locale) {
        Percentile percentile = new Percentile();
        percentile.setValue(LocalTime.parse(text).getMinute());
        return percentile;
    }

    @Override
    public String print(Percentile percentile, Locale locale) {
        Duration duration = Duration.ofHours(percentile.getValue() / 60).plusMinutes(percentile.getValue() % 60);
        return duration.toHours() + ":" + duration.toMinutesPart();
    }
}

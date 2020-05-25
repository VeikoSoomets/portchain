package com.portchain.challenge.domain.task.percentiles;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentileTest {

    @Test
    void shouldGet15thPercentile() {
        Percentile percentile = new Percentile(15);

        percentile.calculateValue(new ArrayList<>() {{
            add(0);
            add(40);
            add(60);
            add(70);
            add(90);
        }});

        assertEquals(0, percentile.getValue());
    }

    @Test
    void shouldGet20thPercentile() {
        Percentile percentile = new Percentile(20);

        percentile.calculateValue(new ArrayList<>() {{
            add(0);
            add(40);
            add(60);
            add(70);
            add(90);
        }});

        assertEquals(20, percentile.getValue());
    }
}
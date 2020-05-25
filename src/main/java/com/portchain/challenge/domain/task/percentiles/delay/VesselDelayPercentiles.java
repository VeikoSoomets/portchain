package com.portchain.challenge.domain.task.percentiles.delay;

import com.portchain.challenge.domain.task.percentiles.Percentile;
import com.portchain.challenge.domain.task.percentiles.VesselWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VesselDelayPercentiles {
    @Getter
    private List<VesselWithPercentiles> vesselsWithPercentiles = new ArrayList<>();

    public void add(int delayedDays, Vessel vessel) {
        List<Integer> delays = vessel.getPortCalls()
                .stream()
                .map(portCall -> portCall.getDelay(delayedDays))
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        if (!delays.isEmpty()) {
            VesselWithPercentiles vesselWithPercentiles = new VesselWithPercentiles(vessel, new Percentile[] {
                    new Percentile(5),
                    new Percentile(50),
                    new Percentile(80)
            });
            vesselsWithPercentiles.add(vesselWithPercentiles);

            vesselWithPercentiles.setValues(delays);
            for (Percentile percentile : vesselWithPercentiles.getPercentiles()) {
                percentile.calculateValue(delays);
            }
        }
    }
}

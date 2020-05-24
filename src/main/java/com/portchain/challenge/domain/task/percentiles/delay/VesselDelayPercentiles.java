package com.portchain.challenge.domain.task.percentiles.delay;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.percentiles.Percentile;
import com.portchain.challenge.domain.task.percentiles.VesselWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VesselDelayPercentiles {
    @Getter
    private List<VesselWithPercentiles> vesselsWithPercentiles = new ArrayList<>();

    public void add(int delayedDays, Vessel vessel) {
        List<Integer> delays = new ArrayList<>();
        Map<Port, List<Integer>> delaysForPorts = new HashMap<>();
        VesselWithPercentiles vesselWithPercentiles = new VesselWithPercentiles(vessel, new Percentile[] {
                new Percentile(5),
                new Percentile(50),
                new Percentile(80)
        });

        vesselsWithPercentiles.add(vesselWithPercentiles);
        for (PortCall portCall : vessel.getPortCalls()) {
            Integer delay = portCall.getDelay(delayedDays);
            if (delay == null) {
                continue;
            }

            delays.add(delay);
            if (!delaysForPorts.containsKey(portCall.getPort())) {
                delaysForPorts.put(portCall.getPort(), new ArrayList<>());
            }
            delaysForPorts.get(portCall.getPort()).add(delay);
        }
        Collections.sort(delays);

        if (!delays.isEmpty()) {
            vesselWithPercentiles.setValues(delays);
            for (Percentile percentile : vesselWithPercentiles.getPercentiles()) {
                percentile.calculateValue(delays);
            }
        }
    }
}

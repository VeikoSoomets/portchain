package com.portchain.challenge.domain.task.percentiles.duration;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.percentiles.Percentile;
import com.portchain.challenge.domain.task.percentiles.PortWithPercentiles;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PortCallDurationPercentiles {
    @Getter
    private List<PortWithPercentiles> portsWithPercentiles = new ArrayList<>();

    public void add(Port port, List<PortCall> portCalls) {
        PortWithPercentiles portWithPercentiles = new PortWithPercentiles(port, new Percentile[] {
                new Percentile(5),
                new Percentile(20),
                new Percentile(50),
                new Percentile(75),
                new Percentile(90)
        });

        portsWithPercentiles.add(portWithPercentiles);

        if (portCalls.size() == 0) {
            portWithPercentiles.setPercentiles(null);
            return;
        }

        List<Integer> durations = portCalls
                .stream()
                .map(PortCall::getDuration)
                .sorted()
                .collect(Collectors.toList());

        for (Percentile percentile : portWithPercentiles.getPercentiles()) {
            percentile.calculateValue(durations);
        }
    }
}

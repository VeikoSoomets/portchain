package com.portchain.challenge.domain.task.topfive.most.arrivals;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.Task;
import com.portchain.challenge.domain.task.TaskUtil;
import com.portchain.challenge.domain.vessel.Vessel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopFivePortsWithMostArrivalsTask implements Task<TopFivePortsWithMostArrivals> {

    public TopFivePortsWithMostArrivals find(List<Vessel> vessels) {
        TopFivePortsWithMostArrivals result = new TopFivePortsWithMostArrivals();
        Map<Port, List<PortCall>> portCallsPerPort = TaskUtil.getPortCallsPerPort(vessels);

        for (Map.Entry<Port, List<PortCall>> portCallsWithPort : portCallsPerPort.entrySet()) {
            Port port = portCallsWithPort.getKey();
            List<PortCall> portCalls = portCallsWithPort.getValue();
            List<PortCall> portCallsWithoutOmit = portCalls
                    .stream()
                    .filter(portCall -> !portCall.isOmitted())
                    .collect(Collectors.toList());

            result.add(port, portCallsWithoutOmit);
        }

        return result;
    }
}

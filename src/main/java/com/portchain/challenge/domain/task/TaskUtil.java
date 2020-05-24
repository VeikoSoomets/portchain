package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.vessel.Vessel;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class TaskUtil {

    public static Map<Port, List<PortCall>> getPortCallsPerPort(List<Vessel> vessels) {
        return vessels
                .stream()
                .flatMap(vessel -> vessel.getPortCalls().stream())
                .collect(groupingBy(PortCall::getPort));
    }
}

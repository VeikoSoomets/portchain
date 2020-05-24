package com.portchain.challenge.domain.task.topfive;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PortWithPortCalls implements Comparable<PortWithPortCalls>{

    private Port port;
    private List<PortCall> portCalls = new ArrayList<>();

    public PortWithPortCalls(Port port, List<PortCall> portCalls) {
        this.port = port;
        this.portCalls = portCalls;
    }

    @Override
    public int compareTo(PortWithPortCalls portWithPortCalls) {
        if (getPort() == null) {
            return 0;
        } else if (getPort() != null && portWithPortCalls.getPort() == null) {
            return -1;
        }

        return Integer.compare(getPortCalls().size(), portWithPortCalls.getPortCalls().size());
    }
}
package com.portchain.challenge.domain.task.topfive.fewest.portcalls;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.topfive.PortWithPortCalls;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class TopFivePortsWithFewestPortCalls {
    @Getter
    private PortWithPortCalls[] portsWithPortCalls = new PortWithPortCalls[] {
            new PortWithPortCalls(),
            new PortWithPortCalls(),
            new PortWithPortCalls(),
            new PortWithPortCalls(),
            new PortWithPortCalls()
    };

    public void add(Port port, List<PortCall> portCalls) {
        PortWithPortCalls portWithPortCalls = new PortWithPortCalls(port, portCalls);
        if (getNrOfMostPortCalls() > portCalls.size() || getLastPort() == null) {
            portsWithPortCalls[4] = portWithPortCalls;
            Arrays.sort(portsWithPortCalls);
        }
    }

    private int getNrOfMostPortCalls() {
        PortWithPortCalls portWithPortCalls = portsWithPortCalls[4];

        if (portWithPortCalls == null) {
            return Integer.MAX_VALUE;
        }
        return portWithPortCalls.getPortCalls().size();
    }

    private Port getLastPort() {
        return portsWithPortCalls[4].getPort();
    }
}
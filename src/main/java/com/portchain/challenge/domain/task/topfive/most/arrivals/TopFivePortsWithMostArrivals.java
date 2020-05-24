package com.portchain.challenge.domain.task.topfive.most.arrivals;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.topfive.PortWithArrivals;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class TopFivePortsWithMostArrivals {
    @Getter
    private PortWithArrivals[] portsWithArrivals = new PortWithArrivals[] {
            new PortWithArrivals(),
            new PortWithArrivals(),
            new PortWithArrivals(),
            new PortWithArrivals(),
            new PortWithArrivals()
    };

    public void add(Port port, List<PortCall> portCalls) {
        PortWithArrivals portWithArrivals = new PortWithArrivals(port, portCalls);
        if (getNrOfLeastArrivals() < portCalls.size() || getLastPort() == null) {
            portsWithArrivals[4] = portWithArrivals;
            Arrays.sort(portsWithArrivals);
        }
    }

    private int getNrOfLeastArrivals() {
        PortWithArrivals lastPortWithArrivals = portsWithArrivals[4];

        if (lastPortWithArrivals == null) {
            return 0;
        }
        return lastPortWithArrivals.getArrivals().size();
    }

    private Port getLastPort() {
        return portsWithArrivals[4].getPort();
    }
}
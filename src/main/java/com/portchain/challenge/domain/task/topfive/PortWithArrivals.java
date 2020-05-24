package com.portchain.challenge.domain.task.topfive;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortWithArrivals extends PortWithPortCalls {
    private List<PortCall> arrivals;

    public PortWithArrivals() {
        this.arrivals = super.getPortCalls();
    }

    public PortWithArrivals(Port port, List<PortCall> arrivals) {
        super(port, arrivals);
        this.arrivals = super.getPortCalls();
    }

    @Override
    public int compareTo(PortWithPortCalls portWithPortCalls) {
        if (getPort() == null) {
            return 0;
        } else if (getPort() != null && portWithPortCalls.getPort() == null) {
            return -1;
        }

        return Integer.compare(portWithPortCalls.getPortCalls().size(), getArrivals().size());
    }
}

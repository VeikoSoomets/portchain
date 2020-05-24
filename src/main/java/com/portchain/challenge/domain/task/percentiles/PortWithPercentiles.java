package com.portchain.challenge.domain.task.percentiles;

import com.portchain.challenge.domain.port.Port;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PortWithPercentiles {
    private Port port;
    private Percentile[] percentiles;

    public PortWithPercentiles(Port port, Percentile[] percentiles) {
        this.port = port;
        this.percentiles = percentiles;
    }
}

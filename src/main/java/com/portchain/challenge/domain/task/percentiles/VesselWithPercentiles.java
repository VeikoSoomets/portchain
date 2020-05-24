package com.portchain.challenge.domain.task.percentiles;

import com.portchain.challenge.domain.vessel.Vessel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VesselWithPercentiles {
    private Vessel vessel;
    private Percentile[] percentiles;
    private List values;

    public VesselWithPercentiles(Vessel vessel, Percentile[] percentiles) {
        this.vessel = vessel;
        this.percentiles = percentiles;
    }
}

package com.portchain.challenge.domain.task.percentiles.delay;

import com.portchain.challenge.domain.task.Task;
import com.portchain.challenge.domain.vessel.Vessel;

import java.util.List;

public class VesselFourteenDayDelayPercentilesTask implements Task<VesselDelayPercentiles> {

    public VesselDelayPercentiles find(List<Vessel> vessels) {
        VesselDelayPercentiles result = new VesselDelayPercentiles();

        for (Vessel vessel : vessels) {
            result.add(14, vessel);
        }
        return result;
    }
}

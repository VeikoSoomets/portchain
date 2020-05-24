package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.task.percentiles.delay.VesselDelayPercentilesTask;
import com.portchain.challenge.domain.task.percentiles.duration.PortCallDurationPercentilesTask;
import com.portchain.challenge.domain.task.topfive.fewest.portcalls.TopFivePortsWithFewestPortCallsTask;
import com.portchain.challenge.domain.task.topfive.most.arrivals.TopFivePortsWithMostArrivalsTask;
import com.portchain.challenge.domain.vessel.Vessel;
import com.portchain.challenge.domain.vessel.VesselService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final VesselService vesselService;

    public TaskService(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    public Result getTaskResult() {
        Result result = new Result();

        List<Vessel> vessels = vesselService.getVessels();
        result.setTopFivePortsWithMostArrivals(new TopFivePortsWithMostArrivalsTask().find(vessels));
        result.setTopFivePortsWithFewestPortCalls(new TopFivePortsWithFewestPortCallsTask().find(vessels));
        result.setPortCallDurationPercentiles(new PortCallDurationPercentilesTask().find(vessels));
        result.setTwoDayVesselDelayPercentiles(new VesselDelayPercentilesTask().find(vessels));
        result.setSevenDayVesselDelayPercentiles(new VesselDelayPercentilesTask().find(vessels));
        result.setFourteenDayVesselDelayPercentiles(new VesselDelayPercentilesTask().find(vessels));
        return result;
    }
}

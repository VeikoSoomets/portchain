package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.task.percentiles.delay.VesselFourteenDayDelayPercentilesTask;
import com.portchain.challenge.domain.task.percentiles.delay.VesselSevenDayDelayPercentilesTask;
import com.portchain.challenge.domain.task.percentiles.delay.VesselTwoDayDelayPercentilesTask;
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
        result.setTwoDayVesselDelayPercentiles(new VesselTwoDayDelayPercentilesTask().find(vessels));
        result.setSevenDayVesselDelayPercentiles(new VesselSevenDayDelayPercentilesTask().find(vessels));
        result.setFourteenDayVesselDelayPercentiles(new VesselFourteenDayDelayPercentilesTask().find(vessels));
        return result;
    }
}

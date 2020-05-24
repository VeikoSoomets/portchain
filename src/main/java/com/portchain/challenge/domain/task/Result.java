package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.task.percentiles.delay.VesselDelayPercentiles;
import com.portchain.challenge.domain.task.percentiles.duration.PortCallDurationPercentiles;
import com.portchain.challenge.domain.task.topfive.fewest.portcalls.TopFivePortsWithFewestPortCalls;
import com.portchain.challenge.domain.task.topfive.most.arrivals.TopFivePortsWithMostArrivals;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private TopFivePortsWithMostArrivals topFivePortsWithMostArrivals;
    private TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls;
    private PortCallDurationPercentiles portCallDurationPercentiles;
    private VesselDelayPercentiles twoDayVesselDelayPercentiles;
    private VesselDelayPercentiles sevenDayVesselDelayPercentiles;
    private VesselDelayPercentiles fourteenDayVesselDelayPercentiles;
}

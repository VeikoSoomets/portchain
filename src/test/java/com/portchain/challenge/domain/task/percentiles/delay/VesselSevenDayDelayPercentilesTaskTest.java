package com.portchain.challenge.domain.task.percentiles.delay;

import com.portchain.challenge.domain.logentry.UpdatedField;
import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.percentiles.Percentile;
import com.portchain.challenge.domain.task.percentiles.VesselWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VesselSevenDayDelayPercentilesTaskTest {
    @InjectMocks
    VesselSevenDayDelayPercentilesTask vesselSevenDayDelayPercentilesTask;

    @Test
    void shouldGetTwoDayDelayPortCallPercentiles() {
        int nrOfVessels = 5;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {5, 5, 5, 5, 5};
        int[][] delaysForEachPort = new int[][] {
                {60, 120, 150, 200, 250},
                {30, 100, 150, 90, 50},
                {25, 80, 200, 110, 30},
                {190, 10, 5, 20, 80},
                {300, 500, 150, 200, 250},
        };

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(),
                ZonedDateTime.now().plusHours(1));
        DataInitializer.initializeVesselPortCallsWithLogEntries(vessels, delaysForEachPort, 7);

        VesselDelayPercentiles vesselDelayPercentiles = vesselSevenDayDelayPercentilesTask.find(vessels);
        for (int i = 0; i < vesselDelayPercentiles.getVesselsWithPercentiles().size(); i++) {
            VesselWithPercentiles vesselWithPercentiles =  vesselDelayPercentiles.getVesselsWithPercentiles().get(i);
            for (int j = 0; j < vesselWithPercentiles.getPercentiles().length; j++) {
                Percentile percentile = new Percentile(vesselWithPercentiles.getPercentiles()[j].getRank());
                List<Integer> delaysAsList = stream(delaysForEachPort[i]).boxed().sorted().collect(Collectors.toList());
                percentile.calculateValue(delaysAsList);
                assertEquals(percentile.getValue(), vesselWithPercentiles.getPercentiles()[j].getValue());
            }
        }
    }

    @Test
    void shouldGetSameTwoDayDelayPortCallPercentilesForSameDelays() {
        int nrOfPorts = 5;
        Vessel vessel = DataInitializer.getVessels().get(0);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {1, 1, 1, 1, 1};
        int[][] delaysForEachPort = new int[][] {
                {60, 60, 60, 60, 60}
        };

        DataInitializer.initializeVesselsWithPortCalls(Collections.singletonList(vessel), ports, nrOfPortCallsForEachPort,
                ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        DataInitializer.initializeVesselPortCallsWithLogEntries(asList(vessel), delaysForEachPort, 7);

        VesselDelayPercentiles vesselDelayPercentiles = vesselSevenDayDelayPercentilesTask.find(Collections.singletonList(vessel));
        for (int i = 0; i < vesselDelayPercentiles.getVesselsWithPercentiles().size(); i++) {
            VesselWithPercentiles vesselWithPercentiles =  vesselDelayPercentiles.getVesselsWithPercentiles().get(i);
            int percentileValue = vesselWithPercentiles.getPercentiles()[0].getValue();
            for (int j = 0; j < vesselWithPercentiles.getPercentiles().length; j++) {
                assertEquals(percentileValue, vesselWithPercentiles.getPercentiles()[j].getValue());
            }
        }
    }

    @Test
    void shouldGetTwoDayDelayPortCallPercentilesForZeroDelays() {
        int nrOfPorts = 5;
        Vessel vessel = DataInitializer.getVessels().get(0);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {1, 1, 1, 1, 1};
        int[][] delaysForEachPort = new int[][] {
                {0, 0, 0, 0, 10}
        };

        DataInitializer.initializeVesselsWithPortCalls(Collections.singletonList(vessel), ports, nrOfPortCallsForEachPort,
                ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        DataInitializer.initializeVesselPortCallsWithLogEntries(Collections.singletonList(vessel), delaysForEachPort, 7);

        VesselDelayPercentiles vesselDelayPercentiles = vesselSevenDayDelayPercentilesTask.find(Collections.singletonList(vessel));
        for (int i = 0; i < vesselDelayPercentiles.getVesselsWithPercentiles().size(); i++) {
            VesselWithPercentiles vesselWithPercentiles =  vesselDelayPercentiles.getVesselsWithPercentiles().get(i);
            for (int j = 0; j < vesselWithPercentiles.getPercentiles().length; j++) {
                Percentile percentile = new Percentile(vesselWithPercentiles.getPercentiles()[j].getRank());
                List<Integer> delaysAsList = stream(delaysForEachPort[i]).boxed().sorted().collect(Collectors.toList());
                percentile.calculateValue(delaysAsList);
                assertEquals(percentile.getValue(), vesselWithPercentiles.getPercentiles()[j].getValue());
            }
        }
    }

    @Test
    void shouldNotGetDelayPercentilesForLogEntriesWithDeparturesOnly() {
        int nrOfPorts = 5;
        Vessel vessel = DataInitializer.getVessels().get(0);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {1, 1, 1, 1, 1};
        int[][] delaysForEachPort = new int[][] {
                {0, 0, 0, 0, 10}
        };

        DataInitializer.initializeVesselsWithPortCalls(Collections.singletonList(vessel), ports, nrOfPortCallsForEachPort,
                ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));

        for (int j = 0; j < vessel.getPortCalls().size(); j++) {
            PortCall portCall = vessel.getPortCalls().get(j);
            int delay = delaysForEachPort[0][j];
            DataInitializer.addLogEntryToPortCall(portCall, UpdatedField.DEPARTURE, portCall.getArrival().minusDays(2),
                    portCall.getArrival().plusMinutes(delay), null);
        }

        VesselDelayPercentiles percentiles = vesselSevenDayDelayPercentilesTask.find(Collections.singletonList(vessel));
        assertEquals(0, percentiles.getVesselsWithPercentiles().size());
    }

    @Test
    void shouldNotGetDelayPercentilesForNoLogEntries() {
        int nrOfVessels = 5;
        int nrOfPorts = 5;
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {1, 1, 1, 1, 1};
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(),
                ZonedDateTime.now().plusHours(1));

        VesselDelayPercentiles percentiles = vesselSevenDayDelayPercentilesTask.find(vessels);
        assertEquals(0, percentiles.getVesselsWithPercentiles().size());
    }
}

package com.portchain.challenge.domain.task.percentiles.duration;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.percentiles.Percentile;
import com.portchain.challenge.domain.task.percentiles.PortWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PortCallDurationPercentilesTaskTest {
    @InjectMocks
    PortCallDurationPercentilesTask portCallDurationPercentilesTask;

    @Test
    void shouldGetCallDurationPercentilesForAllPorts() {
        int nrOfVessels = 5;
        int nrOfPorts = 10;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
            List<Integer> callDurationsForAllPorts = new ArrayList<>() {{
            add(60);
            add(120);
            add(180);
            add(240);
            add(300);
        }};

        for (int i = 0; i < ports.size(); i++) {
            Vessel vessel = vessels.get(i % vessels.size());
            Port port = ports.get(i);

            for (int callDuration : callDurationsForAllPorts) {
                DataInitializer.addPortCallToVessel(vessel, port, ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(callDuration), false);
            }
        }

        PortCallDurationPercentiles portCallDurationPercentiles = portCallDurationPercentilesTask.find(vessels);
        for (PortWithPercentiles portWithPercentiles : portCallDurationPercentiles.getPortsWithPercentiles()) {
            for (int i = 0; i < portWithPercentiles.getPercentiles().length; i++) {
                Percentile percentile = new Percentile(portWithPercentiles.getPercentiles()[i].getRank());
                percentile.calculateValue(callDurationsForAllPorts);
                assertEquals(percentile.getValue(), portWithPercentiles.getPercentiles()[i].getValue());
            }
        }
    }

    @Test
    void shouldGetSameCallDurationPercentilesForOnePortCall() {
        Vessel vessel = DataInitializer.getVessels().get(0);
        Port port = DataInitializer.getPorts().get(0);
        int callDurationForPort = 60;

        DataInitializer.addPortCallToVessel(vessel, port, ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(callDurationForPort), false);
        PortWithPercentiles portWithPercentiles = portCallDurationPercentilesTask.find(Collections.singletonList(vessel)).getPortsWithPercentiles().get(0);
        int callDuration = portWithPercentiles.getPercentiles()[0].getValue();
        for (int i = 0; i < portWithPercentiles.getPercentiles().length; i++) {
            assertEquals(callDuration, portWithPercentiles.getPercentiles()[i].getValue());
        }
    }

    @Test
    void shouldNotGetCallDurationPercentilesForPortCallWithNoDeparture() {
        Vessel vessel = DataInitializer.getVessels().get(0);
        Port port = DataInitializer.getPorts().get(0);

        DataInitializer.addPortCallToVessel(vessel, port, ZonedDateTime.now(), null, false);
        PortWithPercentiles portWithPercentiles = portCallDurationPercentilesTask.find(Collections.singletonList(vessel)).getPortsWithPercentiles().get(0);
        for (int i = 0; i < portWithPercentiles.getPercentiles().length; i++) {
            assertEquals(0, portWithPercentiles.getPercentiles()[i].getValue());
        }
    }

    @Test
    void shouldNotGetCallDurationPercentilesForNoPortCalls() {
        int nrOfVessels = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        PortCallDurationPercentiles portCallDurationPercentiles = portCallDurationPercentilesTask.find(vessels);
        assertEquals(0, portCallDurationPercentiles.getPortsWithPercentiles().size());
    }
}

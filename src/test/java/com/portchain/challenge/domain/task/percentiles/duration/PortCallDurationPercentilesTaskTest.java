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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PortCallDurationPercentilesTaskTest {
    @InjectMocks
    PortCallDurationPercentilesTask portCallDurationPercentilesTask;

    @Test
    void shouldGetPortCallDurationPercentiles() {
        int nrOfVessels = 5;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        List<Integer> callDurationsForAllPorts = new ArrayList<>() {{
            add(60);
            add(120);
            add(180);
            add(240);
            add(300);
        }};
        Percentile[] percentiles = new Percentile[] {
                new Percentile(5),
                new Percentile(20),
                new Percentile(50),
                new Percentile(75),
                new Percentile(90)
        };

        for (int i = 0; i < ports.size(); i++) {
            Vessel vessel = vessels.get(i % ports.size());
            Port port = ports.get(i);

            for (int callDuration : callDurationsForAllPorts) {
                DataInitializer.addPortCallToVessel(vessel, port, ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(callDuration), false);
            }
        }

        PortCallDurationPercentiles portCallDurationPercentiles = portCallDurationPercentilesTask.find(vessels);
        PortWithPercentiles port3WithPercentiles = portCallDurationPercentiles.getPortsWithPercentiles()
                .stream()
                .filter(portWithPercentiles -> portWithPercentiles.getPort().getId().equals("345"))
                .findFirst()
                .get();

        for (int i = 0; i < port3WithPercentiles.getPercentiles().length; i++) {
            percentiles[0].calculateValue(callDurationsForAllPorts);
            assertEquals(percentiles[0].getValue(), port3WithPercentiles.getPercentiles()[i].getValue());
        }
    }
}

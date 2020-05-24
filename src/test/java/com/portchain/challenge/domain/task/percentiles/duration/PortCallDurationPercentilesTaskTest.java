package com.portchain.challenge.domain.task.percentiles.duration;

import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.percentiles.PortWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PortCallDurationPercentilesTaskTest {

    @InjectMocks
    PortCallDurationPercentilesTask portCallDurationPercentilesTask;

    @Test
    void shouldGetPortCallDurationPercentiles() {
        List<Vessel> vessels = DataInitializer.getVessels();
        PortCallDurationPercentiles percentiles = portCallDurationPercentilesTask.find(vessels);

        PortWithPercentiles port3WithPercentiles = percentiles.getPortsWithPercentiles()
                .stream()
                .filter(portWithPercentiles -> portWithPercentiles.getPort().getId().equals("345"))
                .findFirst()
                .get();

        assertEquals(15 * 60, port3WithPercentiles.getPercentiles()[0].getValue());
        assertEquals(15 * 60, port3WithPercentiles.getPercentiles()[1].getValue());
        assertEquals(35 * 60, port3WithPercentiles.getPercentiles()[2].getValue());
        assertEquals(40 * 60, port3WithPercentiles.getPercentiles()[3].getValue());
        assertEquals(50 * 60, port3WithPercentiles.getPercentiles()[4].getValue());
    }
}

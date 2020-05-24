package com.portchain.challenge.domain.task.topfive.most.arrivals;

import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TopFivePortsWithMostArrivalsTaskTest {

    @InjectMocks
    TopFivePortsWithMostArrivalsTask topFivePortsWithMostArrivalsTask;

    @Test
    void shouldGetTopFivePortsWithMostArrivals() {
        List<Vessel> vessels = DataInitializer.getVessels();
        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);

        assertEquals("Port3", topFivePortsWithMostArrivals.getPortsWithArrivals()[0].getPort().getName());
        assertEquals(5, topFivePortsWithMostArrivals.getPortsWithArrivals()[0].getArrivals().size());
        assertEquals("Port1", topFivePortsWithMostArrivals.getPortsWithArrivals()[1].getPort().getName());
        assertEquals(3, topFivePortsWithMostArrivals.getPortsWithArrivals()[1].getArrivals().size());
        assertEquals("Port2", topFivePortsWithMostArrivals.getPortsWithArrivals()[2].getPort().getName());
        assertEquals(2, topFivePortsWithMostArrivals.getPortsWithArrivals()[2].getArrivals().size());
        assertEquals("Port4", topFivePortsWithMostArrivals.getPortsWithArrivals()[3].getPort().getName());
        assertEquals(1, topFivePortsWithMostArrivals.getPortsWithArrivals()[3].getArrivals().size());
        assertEquals("Port5", topFivePortsWithMostArrivals.getPortsWithArrivals()[4].getPort().getName());
        assertEquals(0, topFivePortsWithMostArrivals.getPortsWithArrivals()[4].getArrivals().size());
    }
}

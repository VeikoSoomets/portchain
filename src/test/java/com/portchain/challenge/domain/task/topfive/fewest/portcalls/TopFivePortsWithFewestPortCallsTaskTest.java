package com.portchain.challenge.domain.task.topfive.fewest.portcalls;

import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TopFivePortsWithFewestPortCallsTaskTest {

    @InjectMocks
    TopFivePortsWithFewestPortCallsTask topFivePortsWithFewestPortCallsTask;

    @Test
    void shouldGetTopFivePortsWithFewestPortCalls() {
        List<Vessel> vessels = DataInitializer.getVessels();
        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);

        assertEquals("Port5", topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[0].getPort().getName());
        assertEquals(0, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[0].getPortCalls().size());
        assertEquals("Port4", topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[1].getPort().getName());
        assertEquals(1, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[1].getPortCalls().size());
        assertEquals("Port2", topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[2].getPort().getName());
        assertEquals(2, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[2].getPortCalls().size());
        assertEquals("Port1", topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[3].getPort().getName());
        assertEquals(3, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[3].getPortCalls().size());
        assertEquals("Port3", topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[4].getPort().getName());
        assertEquals(5, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[4].getPortCalls().size());
    }
}

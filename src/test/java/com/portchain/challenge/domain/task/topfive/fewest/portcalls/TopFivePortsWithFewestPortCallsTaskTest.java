package com.portchain.challenge.domain.task.topfive.fewest.portcalls;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.topfive.PortWithPortCalls;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TopFivePortsWithFewestPortCallsTaskTest {
    @InjectMocks
    TopFivePortsWithFewestPortCallsTask topFivePortsWithFewestPortCallsTask;

    @Test
    void shouldGetTopFiveFromTenPorts() {
        int nrOfVessels = 5;
        int nrOfPorts = 10;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithFewestPortCalls.getPortsWithPortCalls().length; i++) {
            Port port = ports.get(nrOfPorts - 1 - i);
            assertEquals(port.getName(), topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPort().getName());
            assertEquals(nrOfPortCallsForEachPort[nrOfPorts - 1 - i], topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPortCalls().size());
        }
    }

    @Test
    void shouldGetTopFivePortsFromFivePorts() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {5, 4, 3, 2, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithFewestPortCalls.getPortsWithPortCalls().length; i++) {
            Port port = ports.get(nrOfPorts - 1 - i);
            assertEquals(port.getName(), topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPort().getName());
            assertEquals(nrOfPortCallsForEachPort[nrOfPorts - 1 - i], topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPortCalls().size());
        }
    }

    @Test
    void shouldNotHaveOmittedPortCallsInTopFive() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {5, 4, 3, 2, 1};
        int[] nrOfOmittedPortCallsForEachPort = new int[] {1, 1, 1, 1, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        DataInitializer.initializeVesselsWithOmittedPortCalls(vessels, ports, nrOfOmittedPortCallsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithFewestPortCalls.getPortsWithPortCalls().length; i++) {
            Port port = ports.get(nrOfPorts - 1 - i);
            assertEquals(port.getName(), topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPort().getName());
            assertEquals(nrOfPortCallsForEachPort[nrOfPorts - 1 - i], topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPortCalls().size());
        }
    }

    @Test
    void shouldGetTopThreePortsForThreePortCalls() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfPortCallsForEachPort = new int[] {1, 1, 1, 0, 0};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfPortCallsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithFewestPortCalls.getPortsWithPortCalls().length; i++) {
            if (i > 5 - 1) {
                assertNull(topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPort());
                assertEquals(0, topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPortCalls().size());
            } else {
                assertEquals(nrOfPortCallsForEachPort[i], topFivePortsWithFewestPortCalls.getPortsWithPortCalls()[i].getPortCalls().size());
            }
        }
    }

    @Test
    void shouldNotGetTopFivePortsForNoVessels() {
        List<Vessel> vessels = new ArrayList<>();

        TopFivePortsWithFewestPortCalls topFivePortsWithFewestPortCalls = topFivePortsWithFewestPortCallsTask.find(vessels);
        for (PortWithPortCalls portWithPorcall : topFivePortsWithFewestPortCalls.getPortsWithPortCalls()) {
            assertNull(portWithPorcall.getPort());
            assertEquals(0, portWithPorcall.getPortCalls().size());
        }
    }
}

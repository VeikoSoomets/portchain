package com.portchain.challenge.domain.task.topfive.most.arrivals;

import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.topfive.PortWithArrivals;
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
public class TopFivePortsWithMostArrivalsTaskTest {
    @InjectMocks
    TopFivePortsWithMostArrivalsTask topFivePortsWithMostArrivalsTask;

    @Test
    void shouldGetTopFivePortsFromTenPorts() {
        int nrOfVessels = 5;
        int nrOfPorts = 10;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfArrivalsForEachPort = new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfArrivalsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithMostArrivals.getPortsWithArrivals().length; i++) {
            Port port = ports.get(i);
            assertEquals(port.getName(), topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getPort().getName());
            assertEquals(nrOfArrivalsForEachPort[i], topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getArrivals().size());
        }
    }

    @Test
    void shouldGetTopFivePortsFromFivePorts() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfArrivalsForEachPort = new int[] {5, 4, 3, 2, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfArrivalsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithMostArrivals.getPortsWithArrivals().length; i++) {
            Port port = ports.get(i);
            assertEquals(port.getName(), topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getPort().getName());
            assertEquals(nrOfArrivalsForEachPort[i], topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getArrivals().size());
        }
    }

    @Test
    void shouldNotHaveOmittedArrivalsInTopFive() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfArrivalsForEachPort = new int[] {5, 4, 3, 2, 1};
        int[] nrOfOmittedArrivalsForEachPort = new int[] {1, 1, 1, 1, 1};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfArrivalsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        DataInitializer.initializeVesselsWithOmittedPortCalls(vessels, ports, nrOfOmittedArrivalsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithMostArrivals.getPortsWithArrivals().length; i++) {
            Port port = ports.get(i);
            assertEquals(port.getName(), topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getPort().getName());
            assertEquals(nrOfArrivalsForEachPort[i], topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getArrivals().size());
        }
    }

    @Test
    void shouldGetTopThreePortsForThreeArrivals() {
        int nrOfVessels = 3;
        int nrOfPorts = 5;
        List<Vessel> vessels = DataInitializer.getVessels().subList(0, nrOfVessels);
        List<Port> ports = DataInitializer.getPorts().subList(0, nrOfPorts);
        int[] nrOfArrivalsForEachPort = new int[] {1, 1, 1, 0, 0};

        DataInitializer.initializeVesselsWithPortCalls(vessels, ports, nrOfArrivalsForEachPort, ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));
        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);
        for (int i = 0; i < topFivePortsWithMostArrivals.getPortsWithArrivals().length; i++) {
            if (i > 5 - 1) {
                assertEquals(null, topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getPort());
                assertEquals(0, topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getArrivals().size());
            } else {
                assertEquals(nrOfArrivalsForEachPort[i], topFivePortsWithMostArrivals.getPortsWithArrivals()[i].getArrivals().size());
            }
        }
    }

    @Test
    void shouldNotGetTopFivePortsForNoVessels() {
        List<Vessel> vessels = new ArrayList<>();

        TopFivePortsWithMostArrivals topFivePortsWithMostArrivals = topFivePortsWithMostArrivalsTask.find(vessels);
        for (PortWithArrivals portsWithArrival : topFivePortsWithMostArrivals.getPortsWithArrivals()) {
            assertEquals(null, portsWithArrival.getPort());
            assertEquals(0, portsWithArrival.getArrivals().size());
        }
    }
}

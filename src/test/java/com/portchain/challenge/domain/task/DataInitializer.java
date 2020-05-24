package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.logentry.LogEntry;
import com.portchain.challenge.domain.logentry.UpdatedField;
import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.vessel.Vessel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: Create test data for departures as well!!
public class DataInitializer {

    public static List<Vessel> getVessels() {
        List<Vessel> vessels = new ArrayList<>();
        Vessel vessel1 = new Vessel(12345, "Vessel1");
        Vessel vessel2 = new Vessel(23456, "Vessel2");
        Vessel vessel3 = new Vessel(34567, "Vessel3");

        Port port1 = new Port("123", "Port1");
        Port port2 = new Port("234", "Port2");
        Port port3 = new Port("345", "Port3");
        Port port4 = new Port("456", "Port4");
        Port port5 = new Port("567", "Port5");

        addPortCallToVessel(vessel2, port3, ZonedDateTime.now(), ZonedDateTime.now().plusHours(15), false);
        addPortCallToVessel(vessel2, port3, ZonedDateTime.now().plusHours(20), ZonedDateTime.now().plusHours(40), false);
        addPortCallToVessel(vessel3, port3, ZonedDateTime.now(), ZonedDateTime.now().plusHours(35), false);
        addPortCallToVessel(vessel1, port3, ZonedDateTime.now(), ZonedDateTime.now().plusHours(40), false);
        addPortCallToVessel(vessel1, port3, ZonedDateTime.now().plusHours(60), ZonedDateTime.now().plusHours(110), false);

        addPortCallToVessel(vessel2, port1, ZonedDateTime.now().plusHours(1), ZonedDateTime.now().plusHours(4), false);
        addPortCallToVessel(vessel3, port1, ZonedDateTime.now().plusHours(1), ZonedDateTime.now().plusHours(6), false);
        addPortCallToVessel(vessel1, port1, ZonedDateTime.now().plusHours(1), ZonedDateTime.now().plusHours(8), false);

        addPortCallToVessel(vessel3, port2, ZonedDateTime.now().plusHours(2), ZonedDateTime.now().plusHours(50), false);
        addPortCallToVessel(vessel1, port2, ZonedDateTime.now().plusHours(3), ZonedDateTime.now().plusHours(70), false);

        addPortCallToVessel(vessel2, port4, ZonedDateTime.now().plusHours(4), ZonedDateTime.now().plusHours(50), false);
        addPortCallToVessel(vessel1, port4, ZonedDateTime.now().plusHours(4), ZonedDateTime.now().plusHours(70), true);

        addPortCallToVessel(vessel3, port5, ZonedDateTime.now().plusHours(5), ZonedDateTime.now().plusHours(9), true);

        PortCall port1PortCall = vessel1.getPortCalls()
                .stream()
                .filter(portCall -> portCall.getPort().getName().equals("Port1"))
                .findFirst()
                .get();

        PortCall port2PortCall = vessel1.getPortCalls()
                .stream()
                .filter(portCall -> portCall.getPort().getName().equals("Port2"))
                .findFirst()
                .get();

        PortCall port3PortCall = vessel1.getPortCalls()
                .stream()
                .filter(portCall -> portCall.getPort().getName().equals("Port3"))
                .findFirst()
                .get();

        addLogEntryToPortCall(port1PortCall, UpdatedField.ARRIVAL, port1PortCall.getArrival(), port1PortCall.getArrival());
        addLogEntryToPortCall(port1PortCall, UpdatedField.ARRIVAL, port1PortCall.getArrival().minusDays(2), port1PortCall.getArrival().minusHours(3));
        addLogEntryToPortCall(port1PortCall, UpdatedField.ARRIVAL, port1PortCall.getArrival().minusDays(14), port1PortCall.getArrival().plusHours(5));

        addLogEntryToPortCall(port2PortCall, UpdatedField.ARRIVAL, port2PortCall.getArrival().minusDays(3).plusHours(5), port2PortCall.getArrival().minusHours(2));
        addLogEntryToPortCall(port2PortCall, UpdatedField.ARRIVAL, port2PortCall.getArrival().minusDays(3), port2PortCall.getArrival().minusHours(5));
        addLogEntryToPortCall(port2PortCall, UpdatedField.ARRIVAL, port2PortCall.getArrival().plusDays(3), port2PortCall.getArrival().minusHours(2));
        addLogEntryToPortCall(port2PortCall, UpdatedField.ARRIVAL, port2PortCall.getArrival().minusDays(30), port2PortCall.getArrival().minusHours(3));

        addLogEntryToPortCall(port3PortCall, UpdatedField.ARRIVAL, port3PortCall.getArrival(), port3PortCall.getArrival());
        addLogEntryToPortCall(port3PortCall, UpdatedField.ARRIVAL, port3PortCall.getArrival().minusDays(2), port3PortCall.getArrival().minusHours(1));
        addLogEntryToPortCall(port3PortCall, UpdatedField.ARRIVAL, port3PortCall.getArrival().minusDays(7).minusHours(3), port3PortCall.getArrival().plusHours(5));
        addLogEntryToPortCall(port3PortCall, UpdatedField.ARRIVAL, port3PortCall.getArrival().minusDays(7), port3PortCall.getArrival().plusHours(7));
        addLogEntryToPortCall(port3PortCall, UpdatedField.ARRIVAL, port3PortCall.getArrival().minusDays(14), port3PortCall.getArrival().plusHours(2));

        vessels.add(vessel1);
        vessels.add(vessel2);
        vessels.add(vessel3);

        return vessels;
    }

    private static void addPortCallToVessel(Vessel vessel, Port port, ZonedDateTime arrival, ZonedDateTime departure, boolean isOmitted) {
        PortCall portCall = new PortCall(arrival, departure);
        portCall.setPort(port);
        portCall.setOmitted(isOmitted);
        vessel.getPortCalls().add(portCall);
    }

    private static void addLogEntryToPortCall(PortCall portCall, UpdatedField updatedField, ZonedDateTime createdDate, ZonedDateTime arrival) {
        LogEntry logEntry = new LogEntry();
        logEntry.setUpdatedField(updatedField);
        logEntry.setArrival(arrival);
        logEntry.setCreatedDate(createdDate);
        portCall.getLogEntries().add(logEntry);
    }
}

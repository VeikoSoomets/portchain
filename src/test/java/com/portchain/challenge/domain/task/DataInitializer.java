package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.logentry.LogEntry;
import com.portchain.challenge.domain.logentry.UpdatedField;
import com.portchain.challenge.domain.port.Port;
import com.portchain.challenge.domain.portcall.PortCall;
import com.portchain.challenge.domain.vessel.Vessel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {

    public static List<Vessel> getVessels() {
        return new ArrayList<>() {{
            add(new Vessel(12345, "Vessel1"));
            add(new Vessel(23456, "Vessel2"));
            add(new Vessel(34567, "Vessel3"));
            add(new Vessel(45678, "Vessel4"));
            add(new Vessel(56789, "Vessel5"));
        }};
    }

    public static List<Port> getPorts() {
        return new ArrayList<>() {{
            add(new Port("123", "Port1"));
            add(new Port("234", "Port2"));
            add(new Port("345", "Port3"));
            add(new Port("456", "Port4"));
            add(new Port("567", "Port5"));
            add(new Port("678", "Port6"));
            add(new Port("789", "Port7"));
            add(new Port("8910", "Port8"));
            add(new Port("91011", "Port9"));
            add(new Port("101112", "Port10"));
        }};
    }

    public static void initializeVesselsWithPortCalls(List<Vessel> vessels, List<Port> ports,
                                                      int[] nrOfPortCallsForEachPort,
                                                      ZonedDateTime arrival, ZonedDateTime departure) {
        for (int i = 0; i < ports.size(); i++) {
            Port port = ports.get(i);
            int nrOfPortsCalls = nrOfPortCallsForEachPort[i];

            for(int j = 0; j < nrOfPortsCalls; j++) {
                Vessel vessel = vessels.get(j % vessels.size());
                DataInitializer.addPortCallToVessel(vessel, port, arrival, departure, false);
            }
        }
    }

    public static void initializeVesselsWithOmittedPortCalls(List<Vessel> vessels, List<Port> ports,
                                                             int[] nrOfOmittedPortCallsForEachPort,
                                                             ZonedDateTime arrival, ZonedDateTime departure) {
        for (int i = 0; i < ports.size(); i++) {
            Port port = ports.get(i);
            int nrOfPortsCalls = nrOfOmittedPortCallsForEachPort[i];

            for(int j = 0; j < nrOfPortsCalls; j++) {
                Vessel vessel = vessels.get(j % vessels.size());
                DataInitializer.addPortCallToVessel(vessel, port, arrival, departure, true);
            }
        }
    }

    public static void initializeVesselPortCallsWithLogEntries(List<Vessel> vessels, int[][] delaysForEachPort, int logEntryAgeInDays) {
        for (int i = 0; i < vessels.size(); i++) {
            Vessel vessel = vessels.get(i);
            for (int j = 0; j < vessel.getPortCalls().size(); j++) {
                PortCall portCall = vessel.getPortCalls().get(j);
                int delay = delaysForEachPort[i][j];
                DataInitializer.addLogEntryToPortCall(portCall, UpdatedField.ARRIVAL, portCall.getArrival().minusDays(2),
                        portCall.getArrival().plusMinutes(delay), null);
            }
        }
    }

    public static void addPortCallToVessel(Vessel vessel, Port port, ZonedDateTime arrival, ZonedDateTime departure,
                                           boolean isOmitted) {
        PortCall portCall = new PortCall(arrival, departure);
        portCall.setPort(port);
        portCall.setOmitted(isOmitted);
        vessel.getPortCalls().add(portCall);
    }

    public static void addLogEntryToPortCall(PortCall portCall, UpdatedField updatedField, ZonedDateTime createdDate,
                                             ZonedDateTime arrival, ZonedDateTime departure) {
        LogEntry logEntry = new LogEntry();
        logEntry.setUpdatedField(updatedField);
        logEntry.setArrival(arrival);
        logEntry.setDeparture(departure);
        logEntry.setCreatedDate(createdDate);
        portCall.getLogEntries().add(logEntry);
    }
}

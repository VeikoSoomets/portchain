package com.portchain.challenge.domain.vessel;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class VesselService {

    private final RestTemplate portchainRestTemplate;

    public VesselService(RestTemplate portchainRestTemplate) {
        this.portchainRestTemplate = portchainRestTemplate;
    }

    public List<Vessel> getVessels() {
        ResponseEntity<Vessel[]> responseForAllVessels = portchainRestTemplate.getForEntity("/vessels", Vessel[].class);
        Vessel[] vessels = responseForAllVessels.getBody();

        for (Vessel vessel: vessels) {
            ResponseEntity<Vessel> responseForVesselSchedule = portchainRestTemplate.getForEntity(
                    "/schedule/{vesselImo}",
                    Vessel.class,
                    vessel.getImo());
            vessel.setPortCalls(responseForVesselSchedule.getBody().getPortCalls());
        }

        return Arrays.asList(vessels);
    }
}

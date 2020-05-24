package com.portchain.challenge.domain.task.percentiles.delay;

import com.portchain.challenge.domain.task.DataInitializer;
import com.portchain.challenge.domain.task.percentiles.VesselWithPercentiles;
import com.portchain.challenge.domain.vessel.Vessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VesselDelayPercentilesTaskTest {
    @InjectMocks
    VesselDelayPercentilesTask vesselDelayPercentilesTask;

    @Test
    void shouldGetTwoDayDelayPortCallPercentiles() {
        List<Vessel> vessels = DataInitializer.getVessels();
        VesselDelayPercentiles percentiles = vesselDelayPercentilesTask.find(vessels);

        VesselWithPercentiles vessel1WithPercentiles = percentiles.getVesselsWithPercentiles()
                .stream()
                .filter(vesselWithPercentiles -> vesselWithPercentiles.getVessel().getName().equals("Vessel1"))
                .findFirst()
                .get();

        assertEquals(60, vessel1WithPercentiles.getPercentiles()[0].getValue());
        assertEquals(2 * 60, vessel1WithPercentiles.getPercentiles()[1].getValue());
        assertEquals(3 * 60, vessel1WithPercentiles.getPercentiles()[2].getValue());
    }

    @Test
    void shouldGetSevenDayDelayPortCallPercentiles() {
        List<Vessel> vessels = DataInitializer.getVessels();
        VesselDelayPercentiles percentiles = vesselDelayPercentilesTask.getSevenDayPercentiles(vessels);

        VesselWithPercentiles vessel1WithPercentiles = percentiles.getVesselsWithPercentiles()
                .stream()
                .filter(vesselWithPercentiles -> vesselWithPercentiles.getVessel().getName().equals("Vessel1"))
                .findFirst()
                .get();

        assertEquals(3* 60, vessel1WithPercentiles.getPercentiles()[0].getValue());
        assertEquals(5 * 60, vessel1WithPercentiles.getPercentiles()[1].getValue());
        assertEquals(7 * 60, vessel1WithPercentiles.getPercentiles()[2].getValue());
    }

    @Test
    void shouldGetFourteenDayDelayPortCallPercentiles() {
        List<Vessel> vessels = DataInitializer.getVessels();
        VesselDelayPercentiles percentiles = vesselDelayPercentilesTask.getFourteenDayPercentiles(vessels);

        VesselWithPercentiles vessel1WithPercentiles = percentiles.getVesselsWithPercentiles()
                .stream()
                .filter(vesselWithPercentiles -> vesselWithPercentiles.getVessel().getName().equals("Vessel1"))
                .findFirst()
                .get();

        assertEquals(2 * 60, vessel1WithPercentiles.getPercentiles()[0].getValue());
        assertEquals(3 * 60, vessel1WithPercentiles.getPercentiles()[1].getValue());
        assertEquals(5 * 60, vessel1WithPercentiles.getPercentiles()[2].getValue());
    }
}

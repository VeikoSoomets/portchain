package com.portchain.challenge.domain.task.percentiles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Percentile {
    private int rank;
    private int value;

    public Percentile(int rank) {
        this.rank = rank;
    }

    public void calculateValue(List<Integer> values){
        double positionOfThePercentile = (double) getRank() / 100.0 * (double) values.size();
        if (positionOfThePercentile % 1 == 0) {
            int intPositionOfThePercentile = (int) positionOfThePercentile;
            value = (values.get(intPositionOfThePercentile - 1) + values.get(intPositionOfThePercentile)) / 2;
        } else {
            int intPositionOfThePercentile = (int) Math.ceil(positionOfThePercentile);
            value = values.get(intPositionOfThePercentile - 1);
        }
    }
}

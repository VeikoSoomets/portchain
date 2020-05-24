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
        int ordinal = (int) Math.ceil((double) getRank() / 100.0 * (double) values.size());
        value = values.get(ordinal - 1);
    }
}

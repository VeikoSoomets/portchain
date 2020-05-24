package com.portchain.challenge.domain.task;

import com.portchain.challenge.domain.vessel.Vessel;

import java.util.List;

public interface Task<T> {
    T find(List<Vessel> vessels);
}

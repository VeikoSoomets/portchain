package com.portchain.challenge.domain.vessel;

import com.portchain.challenge.domain.portcall.PortCall;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Vessel {

    private int imo;
    private String name;
    private List<PortCall> portCalls = new ArrayList<>();

    public Vessel(int imo, String name) {
        this.imo = imo;
        this.name = name;
    }
}

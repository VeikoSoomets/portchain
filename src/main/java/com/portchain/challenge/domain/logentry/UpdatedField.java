package com.portchain.challenge.domain.logentry;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum UpdatedField {
    ARRIVAL("arrival"),
    DEPARTURE("departure"),
    IS_OMITTED("isOmitted");

    @Getter
    @JsonValue
    private String name;

    UpdatedField(String name) {
        this.name = name;
    }
}

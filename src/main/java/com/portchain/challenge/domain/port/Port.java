package com.portchain.challenge.domain.port;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Port {
    private String id;
    private String name;

    public Port(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(id, port.id) && Objects.equals(name, port.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

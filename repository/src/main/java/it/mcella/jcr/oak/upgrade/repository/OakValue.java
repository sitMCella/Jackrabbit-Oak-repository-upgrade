package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.Value;

public class OakValue implements JcrValue {

    private final Value value;

    public OakValue(Value value) {
        this.value = value;
    }

    Value getValue() {
        return value;
    }

}

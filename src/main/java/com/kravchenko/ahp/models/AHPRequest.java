package com.kravchenko.ahp.models;

import java.util.Arrays;

public class AHPRequest {

    private Double[][] criteria;
    private Double[][][] alternatives;

    public Double[][] getCriteria() {
        return criteria;
    }

    public void setCriteria(Double[][] criteria) {
        this.criteria = criteria;
    }

    public Double[][][] getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Double[][][] alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public String toString() {
        return "AhpRequest{" +
                "criteria=" + Arrays.toString(criteria) +
                ", alternatives=" + Arrays.toString(alternatives) +
                '}';
    }
}

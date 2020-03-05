package com.kravchenko.ahp.models;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AHP {

    private int dimensions;

    private PairMatrix criteria;
    private PairMatrix[] alternatives;
    private Double[] priorities;

    public AHP(int dimensions) {
        assert dimensions > 0;
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return dimensions;
    }

    public PairMatrix getCriteria() {
        return criteria;
    }

    public void setCriteria(PairMatrix criteria) {
        this.criteria = criteria;
    }

    public PairMatrix[] getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(PairMatrix[] alternatives) {
        this.alternatives = alternatives;
    }

    public Double[] getPriorities() {
        return priorities;
    }

    public void setPriorities(Double[] priorities) {
        this.priorities = priorities;
    }

    public void calculatePriorities() {
        this.priorities = Arrays.stream(this.alternatives)
                .map(alternative -> IntStream.range(0, this.dimensions)
                    .mapToDouble(idx -> alternative.getEigenVector()[idx] * this.criteria.getEigenVector()[idx])
                    .reduce(0.00, Double::sum)
                ).collect(Collectors.toList())
                .toArray(Double[]::new);
    }


}

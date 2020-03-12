package com.kravchenko.ahp.models;

import com.kravchenko.ahp.service.MatrixOperations;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AHP {

    private PairMatrix criteria;
    private PairMatrix[] alternatives;
    private Double[] priorities;

    public AHP(PairMatrix criteria, PairMatrix[] alternatives) {
        this.criteria = criteria;
        this.alternatives = alternatives;
        calculatePriorities();
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


    /*
    * Calculate global priorities
    */
    public void calculatePriorities() {
        Double[][] criteria = new Double[][] {this.criteria.getEigenVector()};
        Double[][] alternatives = Arrays.stream(this.alternatives)
                .map(PairMatrix::getEigenVector)
                .collect(Collectors.toList())
                .toArray(Double[][]::new);
        Double[][] result = MatrixOperations.transposeMatrix(
                MatrixOperations.multiplyMatrices(
                        MatrixOperations.transposeMatrix(alternatives),
                        MatrixOperations.transposeMatrix(criteria)
                )
        );
        this.priorities = result[0];

    }


}

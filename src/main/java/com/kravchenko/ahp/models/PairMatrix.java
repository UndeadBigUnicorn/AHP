package com.kravchenko.ahp.models;

import com.kravchenko.ahp.service.MatrixOperations;

public class PairMatrix {

    private Double[][] values;
    private Double[] eigenVector;
    private Double maxEigenValue;

    public Double[][] getValues() {
        return values;
    }

    public void setValues(Double[][] values) {
        this.values = values;
    }

    public Double[] getEigenVector() {
        return eigenVector;
    }

    public void setEigenVector(Double[] eigenVector) {
        this.eigenVector = eigenVector;
    }

    public Double getMaxEigenValue() {
        return maxEigenValue;
    }

    public void setMaxEigenValue(Double maxEigenValue) {
        this.maxEigenValue = maxEigenValue;
    }

    public PairMatrix(Double[][] values) {
        this.values = values;
        this.eigenVector = MatrixOperations.eigenVector(values);
        this.maxEigenValue = MatrixOperations.maxEigenValue(values, this.eigenVector);
    }

}

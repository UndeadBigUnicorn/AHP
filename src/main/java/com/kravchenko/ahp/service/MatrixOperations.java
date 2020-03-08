package com.kravchenko.ahp.service;

import com.kravchenko.ahp.exceptions.MatrixComputationException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixOperations {

    /*
    * Compute eigen vector for the matrix
    */
    public static Double[] eigenVector(Double[][] matrix) {
        int n = matrix.length;

        if (n == 0) {
            throw new MatrixComputationException("Matrix size has to be more than 0");
        }

        List<Double> tempVectors = Arrays.stream(matrix)
                .map(row-> {
                    double product = Arrays.stream(row)
                            .reduce(1.00, (acc, v)-> acc*v);
                    return Math.pow(product, 1.00/n);
                })
                .collect(Collectors.toList());

        double tempVectorsSum = tempVectors.stream()
                .reduce(0.00, Double::sum);

        return tempVectors.stream()
                .map(value -> value/tempVectorsSum)
                .collect(Collectors.toList())
                .toArray(Double[]::new);
    }


    /*
    * Compute max eigen value for the matrix
    */
    public static double maxEigenValue(Double[][] matrix, Double[] eigenVector) {
        Double[] sums = Arrays.stream(transposeMatrix(matrix))
                .map(row -> Arrays.stream(row).reduce(0.00, Double::sum))
                .collect(Collectors.toList())
                .toArray(Double[]::new);

        return IntStream.range(0, matrix.length)
                .mapToDouble(idx -> sums[idx] * eigenVector[idx])
                .reduce(0.00, Double::sum);
    }


    /*
    *  Transpose matrix
    */
    public static Double[][] transposeMatrix(Double [][] m){
        Double[][] temp = new Double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }


    /*
    * Multiply 2 matrices
    */
    public static Double[][] multiplyMatrices(Double[][] firstMatrix, Double[][] secondMatrix) {
        Double[][] result = new Double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }


    /*
    * Multiply cells of matrices
    */
    public static double multiplyMatricesCell(Double[][] firstMatrix, Double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static Double[][] subtract(Double[][] a, Double[][] b) {
        int rows = a.length;
        int columns = a[0].length;
        Double[][] result = new Double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }


}

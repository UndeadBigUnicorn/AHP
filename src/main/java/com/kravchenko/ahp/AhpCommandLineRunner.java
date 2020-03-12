package com.kravchenko.ahp;

import com.kravchenko.ahp.service.MatrixOperations;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class AhpCommandLineRunner implements CommandLineRunner {

    private final static double tau = 1.2;
    private final static int maxGrade = 30;

    private final static Double[][] pairMatrixWithDefaultScale = new Double[][]{{1.00, 3.00, 5.00}, {0.33, 1.00, 3.00}, {0.25, 0.33, 1.00}};
    private final static String[][] stringPairMatrixWithDefaultScale = new String[][]{{"1", "3", "5"}, {"1/3", "1", "3"}, {"1/5", "1/3", "1"}};

    private final static Double[][] pairMatrixWithTauScale = new Double[][]{{1.00, tau, Math.pow(tau, 2)}, {1.00/tau, 1.00, tau }, {1.00/Math.pow(tau,2), 1.00/tau, 1.00}};
    private final static String[][] stringPairMatrixWithTauScale = new String[][]{{"1", "τ", "τ^2"}, {"1/τ", "1", "τ"}, {"1/τ^2", "1/τ", "1"}};

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Matrix with default 1-9 scale:");
        printNiceMatrix(stringPairMatrixWithDefaultScale);
        Double[] eigenVectorWithDefaultScale = MatrixOperations.eigenVector(pairMatrixWithDefaultScale);
        System.out.println("Eigen Vector for the given pair matrix: \n" + Arrays.toString(eigenVectorWithDefaultScale));
        printGrades(eigenVectorWithDefaultScale);

        System.out.println("\n" + "-".repeat(50) + "\n");

        System.out.println("Matrix with custom 'τ = 1.2' constant value:");
        printNiceMatrix(stringPairMatrixWithTauScale);
        Double[] eigenVectorWithTauScale = MatrixOperations.eigenVector(pairMatrixWithTauScale);
        System.out.println("Eigen Vector for the given pair matrix : \n" + Arrays.toString(eigenVectorWithTauScale));
        printGrades(eigenVectorWithTauScale);
    }

    private void printNiceMatrix(String[][] matrix) {
        for (String[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private void printGrades(Double[] eigenVector) {
        List<Student> students = new LinkedList<>();

        for (int i = 0; i < eigenVector.length; i++) {
            students.add(new Student(i+1 + " Student", eigenVector[i]));
        }

        students.sort((a, b) -> (int) Math.ceil(b.getRating() - a.getRating()));

        double maxRating = students.get(0).getRating();
        students.forEach(s -> s.setGrade((int) ((s.getRating() / maxRating) * maxGrade)));

        System.out.println("Grades for each student: \n" + students);
    }

    private class Student {
        private String name;
        private double rating;
        private int grade;

        public Student(String name, double rating) {
            this.name = name;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", rating=" + rating +
                    ", grade=" + grade +
                    '}';
        }
    }
}


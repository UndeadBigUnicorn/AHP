package com.kravchenko.ahp.service;

import com.kravchenko.ahp.models.AHP;
import com.kravchenko.ahp.models.AHPRequest;
import com.kravchenko.ahp.models.PairMatrix;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class AHPService {

    private static final String[] criteriaTitles = new String[] {
            "Range", "Price", "Speed", "Storage",
            "Comfort", "Charging Speed"};

    private static final String[] alternativesTitles = new String[] {
            "Tesla Model 3", "Nissan Leaf", "HYUNDAI IONIQ", "VW e-Golf"};

    public Double[] solveAHP(AHPRequest request) {
        AHP ahp = new AHP(request.getCriteria().length);
        ahp.setCriteria(computeCriteria(request));
        ahp.setAlternatives(computeAlternatives(request));
        ahp.calculatePriorities();
        return ahp.getPriorities();
    }

    private PairMatrix computeCriteria(AHPRequest request) {
        return new PairMatrix(request.getCriteria(), criteriaTitles);
    }

    private PairMatrix[] computeAlternatives(AHPRequest request) {
        return Arrays.stream(request.getAlternatives())
                .map(matrix -> new PairMatrix(matrix, alternativesTitles))
                .collect(Collectors.toList())
                .toArray(PairMatrix[]::new);
    }

}

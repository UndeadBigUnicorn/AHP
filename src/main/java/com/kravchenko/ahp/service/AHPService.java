package com.kravchenko.ahp.service;

import com.kravchenko.ahp.models.AHP;
import com.kravchenko.ahp.models.AHPRequest;
import com.kravchenko.ahp.models.PairMatrix;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class AHPService {

    public Double[] solveAHP(AHPRequest request) {
        return new AHP(computeCriteria(request), computeAlternatives(request))
                .getPriorities();
    }

    private PairMatrix computeCriteria(AHPRequest request) {
        return new PairMatrix(request.getCriteria());
    }

    private PairMatrix[] computeAlternatives(AHPRequest request) {
        return Arrays.stream(request.getAlternatives())
                .map(PairMatrix::new)
                .collect(Collectors.toList())
                .toArray(PairMatrix[]::new);
    }

}

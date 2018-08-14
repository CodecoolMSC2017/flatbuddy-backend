package com.codecool.flatbuddy.util;

import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.model.Message;
import com.codecool.flatbuddy.model.RentAd;
import org.aspectj.weaver.ast.Instanceof;

import java.util.ArrayList;
import java.util.List;

public class DisabilityChecker {
    public static List<?> checkObjectsIsEnabled(List<?> list) {
        if (list.isEmpty()) {
            return list;
        }
        if (list.get(0) instanceof Match) {
            List<Match> visibleMatches = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Match element = (Match) list.get(i);
                if (element.isEnabled()) {
                    visibleMatches.add(element);
                }
            }
            return visibleMatches;
        } else if (list.get(0) instanceof RentAd) {
            List<RentAd> visibleRentAds = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                RentAd element = (RentAd) list.get(i);
                if (element.isEnabled()) {
                    visibleRentAds.add(element);
                }
            }
            return visibleRentAds;
        }

        return list;
    }
}

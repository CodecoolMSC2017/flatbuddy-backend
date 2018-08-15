package com.codecool.flatbuddy.util;

import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementSpecificationBuilder {
    private final List<SearchCriteria> params;

    public AdvertisementSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public AdvertisementSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<RentAd> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<RentAd>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new AdvertisementSpecification(param));
        }

        Specification<RentAd> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}

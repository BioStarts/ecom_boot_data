package com.geekbrains.bootdata.repositories;

import com.geekbrains.bootdata.entities.Item;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ItemSpecifications {
    public static Specification<Item> titleContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Item> priceGreaterThanOrEq(int value) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
        };
    }

    public static Specification<Item> priceLesserThanOrEq(int value) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
        };
    }
}

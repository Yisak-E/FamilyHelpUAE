package com.example.familyhelpuae.specification;

import com.example.familyhelpuae.model.CommunityPost;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CommunityPostSpecifications {

    public static Specification<CommunityPost> withFilters(String type, String category, String status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Type-safe filtering for Post Type (OFFER/SEEK)
            if (type != null && !type.isEmpty() && !type.equalsIgnoreCase("ALL")) {
                predicates.add(criteriaBuilder.equal(root.get("postType"), type.toUpperCase()));
            }

            // Type-safe filtering for Category
            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category.toLowerCase()));
            }

            // Type-safe filtering for Status (OPEN/IN_PROGRESS)
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status.toUpperCase()));
            }

            // Sort by createdAt descending by default
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
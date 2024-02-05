package com.nova.toolkit;


import com.nova.ticket.model.TicketInfo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TicketSpecification {
    public static Specification<TicketInfo> filtrarFolio(TicketFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getFecha() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), filtro.getFecha()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
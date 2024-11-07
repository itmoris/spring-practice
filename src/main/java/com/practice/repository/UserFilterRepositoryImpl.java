package com.practice.repository;

import com.practice.domain.dto.UserFilterDto;
import com.practice.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UserFilterRepositoryImpl implements UserFilterRepository {
    private final EntityManager em;

    @Override
    @Transactional
    public List<User> findAllByFilter(UserFilterDto filterDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(filterDto.username())) {
            predicates.add(cb.like(root.get("username"), "%" + filterDto.username() + "%"));
        }

        if (nonNull(filterDto.role())) {
            predicates.add(cb.equal(root.get("role"), filterDto.role()));
        }

        query = query.select(root).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getResultList();
    }
}

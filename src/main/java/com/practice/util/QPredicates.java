package com.practice.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {
    private final List<Predicate> predicate = new ArrayList<>();

    public static QPredicates builder() {
        return new QPredicates();
    }

    public <T> QPredicates add(Function<T, Predicate> func, T value) {
        if (nonNull(value)) {
            this.predicate.add(func.apply(value));
        }
        return this;
    }

    public Predicate build() {
        if (predicate.isEmpty()) {
            return Expressions.TRUE;
        }

        return ExpressionUtils.allOf(predicate.toArray(new Predicate[0]));
    }
}

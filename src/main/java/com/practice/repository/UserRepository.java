package com.practice.repository;

import com.practice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
}

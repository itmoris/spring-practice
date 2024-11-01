package com.practice.integration.repository;

import com.practice.integration.IntegrationTest;
import com.practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@IntegrationTest
@RequiredArgsConstructor
class OrderRepositoryIT {
    private final OrderRepository orderRepository;
}
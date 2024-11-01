package com.practice.integration.repository;

import com.practice.integration.IntegrationTest;
import com.practice.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;

@IntegrationTest
@RequiredArgsConstructor
class ShoppingCartRepositoryIT {
    private final ShoppingCartRepository shoppingCartRepository;
}
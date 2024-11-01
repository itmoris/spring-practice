package com.practice.integration.repository;

import com.practice.integration.IntegrationTest;
import com.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@IntegrationTest
@RequiredArgsConstructor
class ProductRepositoryIT {
    private final ProductRepository productRepository;
}
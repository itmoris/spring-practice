package com.practice.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleService {
    private final Map<Integer, String> users = new ConcurrentHashMap<>();

    {
        users.put(1, "andrew");
        users.put(2, "holloway");
        users.put(3, "mcgregor");
        users.put(4, "justin");
    }

    public String findById(Integer id) {
        return users.get(id);
    }
}

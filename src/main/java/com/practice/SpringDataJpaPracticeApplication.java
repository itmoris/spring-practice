package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.learn", "com.practice"})
public class SpringDataJpaPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaPracticeApplication.class, args);
    }

}

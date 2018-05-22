package com.acegear.horizon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrderPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderPayApplication.class, args);
    }
}

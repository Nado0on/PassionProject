package com.photo.shoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.photo.shoot.repository")
public class PhotoShootMain {
    public static void main(String[] args) {
        SpringApplication.run(PhotoShootMain.class, args);
    }
}
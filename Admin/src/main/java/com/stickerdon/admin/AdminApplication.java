package com.stickerdon.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.stickerdon.library.*", "com.stickerdon.admin.*"})
@EnableJpaRepositories(value = "com.stickerdon.library.repository")
@EntityScan(value = "com.stickerdon.library.model")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}

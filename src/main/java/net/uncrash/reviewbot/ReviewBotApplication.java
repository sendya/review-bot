package net.uncrash.reviewbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * @author Sendya
 */
@EnableAsync
@SpringBootApplication
public class ReviewBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ReviewBotApplication.class, args);
    }

}

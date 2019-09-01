package com.intuit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Sunil on 8/23/19.
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application {
    public static void main(String... args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}

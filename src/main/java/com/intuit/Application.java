package com.intuit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sunil on 8/23/19.
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String... args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}

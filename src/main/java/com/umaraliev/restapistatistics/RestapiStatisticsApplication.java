package com.umaraliev.restapistatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@PropertySource("classpath:application.properties")
public class RestapiStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestapiStatisticsApplication.class, args);
    }


}

package com.umaraliev.restapistatistics.service;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalTime;
import java.util.logging.Logger;

@Component
@Async
public class ScheduledTasks {

    static final Logger LOGGER = Logger.getLogger(ScheduledTasks.class.getName());

    private final CompanyService companyService;

    private final StatisticsService statisticsService;

    public ScheduledTasks(CompanyService companyService, StatisticsService statisticsService) {
        this.companyService = companyService;
        this.statisticsService = statisticsService;
    }



    @PostConstruct
    @Scheduled(cron = "${interval-in-get-symbol}")
    public void getSymbol() {
        LOGGER.info("INFO: Getting information " + "|" + LocalTime.now());
        companyService.save();
    }


    @Scheduled(cron = "${interval-in-get-statistics}")
    public void getStatistics() {
        LOGGER.info("INFO: Getting statistics " + "|" + LocalTime.now());
        statisticsService.save();
    }


    @Scheduled(fixedDelay = 5000)
    public void outputExpensiveStocks() {
        System.out.println("-----------------------------------------------------------------------------------------");
        statisticsService.getExpensiveStocks().stream().forEach(System.out::println);
        LOGGER.info("INFO: Expensive stocks " + "|" + LocalTime.now());
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    @Scheduled(fixedDelay = 5000)
    public void outputChangedPrices() {
        System.out.println("-----------------------------------------------------------------------------------------");
        statisticsService.getChangedPrices().stream().forEach(System.out::println);
        LOGGER.info("INFO: Changed prices " + "|" + LocalTime.now());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

}

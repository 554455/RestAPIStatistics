package com.umaraliev.restapistatistics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;

@Component
@Async
@Slf4j
public class ScheduledTasks {

    private final CompanyService companyService;

    private final StatisticsService statisticsService;

    public ScheduledTasks(CompanyService companyService, StatisticsService statisticsService) {
        this.companyService = companyService;
        this.statisticsService = statisticsService;
    }

    @PostConstruct
    @Scheduled(cron = "${interval-in-get-symbol}")
    public void getSymbol() {
        log.info("INFO: Getting information " + "|" + LocalTime.now());
        companyService.getCompanyEntityAll();
    }

    @Scheduled(cron = "${interval-in-get-statistics}")
    public void getStatistics() {
        log.info("INFO: Getting statistics " + "|" + LocalTime.now());
        statisticsService.getStatisticsEntityAll();
    }


    @Scheduled(fixedDelay = 5000)
    public void outputExpensiveStocks() {
        statisticsService.getExpensiveStocks().stream().forEach(System.out::println);
        log.info("INFO: Expensive stocks " + "|" + LocalTime.now());
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    @Scheduled(fixedDelay = 5000)
    public void outputChangedPrices() {
        statisticsService.getChangedPrices().stream().forEach(System.out::println);
        log.info("INFO: Changed prices " + "|" + LocalTime.now());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

}

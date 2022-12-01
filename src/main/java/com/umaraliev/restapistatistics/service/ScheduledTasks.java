package com.umaraliev.restapistatistics.service;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AutoConfiguration
public class ScheduledTasks {

    static final Logger LOGGER = Logger.getLogger(ScheduledTasks.class.getName());

    private final CompanyService companyService;

    private final StatisticsService statisticsService;

    public ScheduledTasks(CompanyService companyService, StatisticsService statisticsService) {
        this.companyService = companyService;
        this.statisticsService = statisticsService;
    }


    @Scheduled(fixedDelay = 1)
    public void getSymbol() {

        LOGGER.info("INFO: Getting information ");
        companyService.save();

    }

    @Scheduled(cron = "${interval-in-cron}")
    public void getStatistics() {

        statisticsService.save();


        LOGGER.info("INFO: Changed prices");
        statisticsService.getChangedPrices();

    }

    @Scheduled(cron = "${interval-in-out}")
    public void outputExpensiveStocks() {
        LOGGER.info("INFO: Expensive stocks ");
        statisticsService.getExpensiveStocks();
    }

    @Scheduled(cron = "${interval-in-out}")
    public void outputChangedPrices() {
        LOGGER.info("INFO: Changed prices");
        statisticsService.getChangedPrices();
    }

}

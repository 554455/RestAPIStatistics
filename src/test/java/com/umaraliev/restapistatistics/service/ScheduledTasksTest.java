package com.umaraliev.restapistatistics.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ScheduledTasksTest {

    private final CompanyService companyService;
    private final StatisticsService statisticsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSymbol() {
        companyService.saveCompanyDetails();
        assertNotNull(companyService.listAll());
    }

    @Test
    void getStatistics() {
        statisticsService.saveStatisticsDetails();
        assertNotNull(statisticsService.listAll());
    }

    @Test
    void outputExpensiveStocks() {
        assertNotNull(statisticsService.getExpensiveStocks());
    }

    @Test
    void outputChangedPrices() {
        assertNotNull(statisticsService.getChangedPrices());
    }
}
package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.model.StatisticsEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(MockitoJUnitRunner.class)
class ScheduledTasksTest {

    @Mock
    private final CompanyService companyService = mock(CompanyService.class);
    private final StatisticsService statisticsService;

    @InjectMocks
    private CompanyEntity company;

    private StatisticsEntity statisticsEntity;

    List<CompanyEntity> companyEntities = new LinkedList<>();
    List<StatisticsEntity> statisticsEntities = new LinkedList<>();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSymbol() {

        company.setId(1L);
        company.setSymbol("-H");
        companyEntities.add(company);

        companyService.saveCompanyDetails(companyEntities);

        assertNotNull(companyService.listAll());
        when(companyService.getOneCompanyEntity(1L)).thenReturn(company);
    }

    @Test
    void getStatistics() {

        statisticsEntity.setId(1L);
        statisticsEntity.setCompanyName("Any name company");
        statisticsEntity.setLatestPrice(1.0);
        statisticsEntity.setPreviousVolume(2);

        statisticsEntities.add(statisticsEntity);

        statisticsService.saveStatisticsDetails(statisticsEntities);

        when(statisticsService.listAll()).thenReturn(statisticsEntities);
    }

}
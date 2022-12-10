package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.model.StatisticsEntity;
import com.umaraliev.restapistatistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CompanyService companyService;
    private final RestTemplate restTemplate;

    @Value("${iex.api.host.statistics}")
    private String iexApiHost;

    @Value("${iex.api.key.statistics}")
    private String iexApiKey;

    ExecutorService fixedPool = Executors.newFixedThreadPool(8);

    public void saveStatisticsDetails() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            statisticsRepository.deleteAll();
            List<CompanyEntity> companies = companyService.listAll();
            for (CompanyEntity company : companies) {
                StatisticsEntity statistics = restTemplate
                        .getForObject(iexApiHost + company.getSymbol() + iexApiKey, StatisticsEntity.class);
                statisticsRepository.save(statistics);
            }
        }, fixedPool);

    }

    public List<StatisticsEntity> listAll() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public List<StatisticsEntity> getExpensiveStocks() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null && s.getPreviousVolume() != null)
                .sorted(Comparator.comparingInt(StatisticsEntity::getPreviousVolume).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<StatisticsEntity> getChangedPrices() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null && s.getLatestPrice() != null)
                .sorted(Comparator.comparingDouble(StatisticsEntity::getLatestPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}

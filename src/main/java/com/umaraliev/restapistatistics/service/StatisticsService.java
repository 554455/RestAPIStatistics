package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.model.StatisticsEntity;
import com.umaraliev.restapistatistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CompanyService companyService;

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    @Value("${iex.api.host.statistics}")
    private String iexApiHost;

    @Value("${iex.api.key.statistics}")
    private String iexApiKey;

    ExecutorService fixedPool = Executors.newFixedThreadPool(8);


    public void saveStatisticsDetails(List<StatisticsEntity> statisticsEntities) {
        if (!CollectionUtils.isEmpty(statisticsEntities)) {
            statisticsRepository.saveAll(statisticsEntities);
        }
    }

    public List<StatisticsEntity> getStatisticsEntityAll() {
        List<StatisticsEntity> statisticsEntities = new LinkedList<>();
        List<CompanyEntity> companies = companyService.listAll();
        CompletableFuture.runAsync(() -> {
            for (CompanyEntity company : companies) {
                Mono<StatisticsEntity> responseEntityFlux = webClient
                        .get()
                        .uri(iexApiHost + company.getSymbol() + iexApiKey)
                        .retrieve()
                        .bodyToMono(StatisticsEntity.class);
                responseEntityFlux.subscribe(statisticsEntities::add);
            }
        }, fixedPool);
        saveStatisticsDetails(statisticsEntities);
        return statisticsEntities;
    }

    public List<StatisticsEntity> listAll() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null && s.getCompanyName() != s.getCompanyName())
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

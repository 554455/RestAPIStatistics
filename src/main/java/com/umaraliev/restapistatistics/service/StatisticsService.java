package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.model.StatisticsEntity;
import com.umaraliev.restapistatistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
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


    public void saveStatisticsDetails() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            List<CompanyEntity> companies = companyService.listAll();
            List<StatisticsEntity> statisticsEntities = new LinkedList<>();
            for (CompanyEntity company : companies) {
                Flux<StatisticsEntity> responseEntityFlux = webClient
                        .get()
                        .uri(iexApiHost + company.getSymbol() + iexApiKey)
                        .retrieve()
                        .bodyToFlux(StatisticsEntity.class);
                responseEntityFlux.collectList().subscribe(statisticsEntities::addAll);
                statisticsRepository.saveAll(statisticsEntities);
        }}, fixedPool);
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

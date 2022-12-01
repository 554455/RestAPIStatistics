package com.umaraliev.restapistatistics.repository;

import com.umaraliev.restapistatistics.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}

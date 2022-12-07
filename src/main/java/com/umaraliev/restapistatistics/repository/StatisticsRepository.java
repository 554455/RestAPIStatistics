package com.umaraliev.restapistatistics.repository;

import com.umaraliev.restapistatistics.model.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {
}

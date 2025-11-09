package com.noorain.currencyconverter.repository;

import com.noorain.currencyconverter.model.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionHistoryRepo extends JpaRepository<ConversionHistory, Long> {
}
//JpaRepository gives you ready-made CRUD operations — no need to write SQL manually.
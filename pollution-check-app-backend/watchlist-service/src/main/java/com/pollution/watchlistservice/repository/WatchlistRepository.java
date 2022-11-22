package com.pollution.watchlistservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pollution.watchlistservice.domain.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    
    List<Watchlist> findByUserEmail(String userEmail);

    Optional<Watchlist> findByUserEmailAndCityAndState(String userEmail, String city, String state);
}

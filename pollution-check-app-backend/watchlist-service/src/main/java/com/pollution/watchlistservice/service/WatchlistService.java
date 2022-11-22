package com.pollution.watchlistservice.service;

import com.pollution.watchlistservice.domain.Watchlist;
import com.pollution.watchlistservice.exceptions.CityDataNotFoundException;
import java.util.List;

public interface WatchlistService {
  Watchlist addToWatchlist(Watchlist requestData);

  void remove(Integer id) throws CityDataNotFoundException;

  List<Watchlist> findCityDataByUserEmail(String userEmail);

  Watchlist updateAqiUS(Watchlist requestData);
}

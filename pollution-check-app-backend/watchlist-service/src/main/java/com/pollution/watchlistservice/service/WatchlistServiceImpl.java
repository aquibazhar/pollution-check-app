package com.pollution.watchlistservice.service;

import com.pollution.watchlistservice.domain.Watchlist;
import com.pollution.watchlistservice.exceptions.CityDataNotFoundException;
import com.pollution.watchlistservice.repository.WatchlistRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {
  private WatchlistRepository watchlistRepository;

  public WatchlistServiceImpl(WatchlistRepository watchlistRepository) {
    this.watchlistRepository = watchlistRepository;
  }

  @Override
  public Watchlist addToWatchlist(Watchlist requestData) {
    Optional<Watchlist> cityDataOptional = watchlistRepository.findByUserEmailAndCityAndState(
      requestData.getUserEmail(),
      requestData.getCity(),
      requestData.getState()
    );
    if (cityDataOptional.isPresent()) {
      return null;
    }
    return watchlistRepository.save(requestData);
  }

  @Override
  public void remove(Integer id) throws CityDataNotFoundException {
    Optional<Watchlist> cityDataOptional = watchlistRepository.findById(id);
    if (cityDataOptional.isEmpty()) {
      throw new CityDataNotFoundException(
        "The data for this city doesn't exist"
      );
    }
    watchlistRepository.deleteById(id);
  }

  @Override
  public List<Watchlist> findCityDataByUserEmail(String userEmail) {
    return watchlistRepository.findByUserEmail(userEmail);
  }

  @Override
  public Watchlist updateAqiUS(Watchlist requestData) {
    Optional<Watchlist> existingWatchlistOptional = watchlistRepository.findById(
      requestData.getId()
    );
    Watchlist existingWatchlist;
    if (existingWatchlistOptional.isPresent()) {
      existingWatchlist = existingWatchlistOptional.get();
      existingWatchlist.setAqiUS(requestData.getAqiUS());
      existingWatchlist.setHealthStatus(requestData.getHealthStatus());
      return watchlistRepository.save(existingWatchlist);
    }
    return null;
  }
}

package com.pollution.watchlistservice.controller;

import java.util.List;

import com.pollution.watchlistservice.dto.WatchlistDto;
import com.pollution.watchlistservice.exceptions.CityDataAlreadyExistsException;
import com.pollution.watchlistservice.service.WatchlistServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.pollution.watchlistservice.domain.Watchlist;
import com.pollution.watchlistservice.exceptions.CityDataNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v3/watchlist")
@CrossOrigin(origins = "http://localhost:4200")
public class WatchlistController {
    
    private WatchlistServiceImpl service;

    public WatchlistController(WatchlistServiceImpl service) {
        this.service = service;
    }


    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Watchlist>> findAll(@PathVariable String userEmail) throws CityDataNotFoundException {

        List<Watchlist> response = service.findCityDataByUserEmail(userEmail);
        if(response.isEmpty()){
            throw new CityDataNotFoundException("The data for this city doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping({"", "/"})
    public ResponseEntity<Watchlist> add(@RequestBody WatchlistDto requestData) throws CityDataAlreadyExistsException {
        Watchlist watchlist = new Watchlist(requestData);
        Watchlist response= service.addToWatchlist(watchlist);
        if(response == null){
            throw new CityDataAlreadyExistsException("The data for this city already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Watchlist> remove(@PathVariable Integer id) throws CityDataNotFoundException {
        service.remove(id);
       return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping({"", "/"})
    public ResponseEntity<Watchlist> updateAqiUS(@RequestBody WatchlistDto requestData) throws CityDataNotFoundException {
        Watchlist watchlist = new Watchlist(requestData);
        Watchlist response = service.updateAqiUS(watchlist);
        if(response == null){
            throw new CityDataNotFoundException("The data for this city doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}

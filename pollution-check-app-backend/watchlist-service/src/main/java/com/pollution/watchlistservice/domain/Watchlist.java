package com.pollution.watchlistservice.domain;

import com.pollution.watchlistservice.dto.WatchlistDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "watchlist")
public class Watchlist {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String userEmail;
    private String city;
    private String state;
    private String country;
    private String aqiUS;
    private String healthStatus;

    public Watchlist(WatchlistDto watchlistDto) {
        this.id = watchlistDto.getId();
        this.userEmail = watchlistDto.getUserEmail();
        this.city = watchlistDto.getCity();
        this.state = watchlistDto.getState();
        this.country = watchlistDto.getCountry();
        this.aqiUS = watchlistDto.getAqiUS();
        this.healthStatus = watchlistDto.getHealthStatus();
    }

    public Watchlist(String userEmail, String city, String state, String country, String aqiUS, String healthStatus) {
        this.userEmail = userEmail;
        this.city = city;
        this.state = state;
        this.country = country;
        this.aqiUS = aqiUS;
        this.healthStatus = healthStatus;
    }
}

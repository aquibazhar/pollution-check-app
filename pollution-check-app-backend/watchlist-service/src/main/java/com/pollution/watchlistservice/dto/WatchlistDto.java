package com.pollution.watchlistservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistDto {

    private Integer id;
    private String userEmail;
    private String city;
    private String state;
    private String country;
    private String aqiUS;
    private String healthStatus;
}

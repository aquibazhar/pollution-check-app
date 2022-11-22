package com.pollution.apiservice.service;

import com.pollution.apiservice.domain.*;

import java.util.List;

public interface ApiService {
    public Country getAllCountries();

    public State getAllStates(String country);

    public City getAllCities(String country, String state);

    public NearestCity getNearestCityData();

    public CityAQI getCityDataByCityName(String country, String state, String city);
}

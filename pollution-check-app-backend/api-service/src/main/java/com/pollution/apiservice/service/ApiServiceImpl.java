package com.pollution.apiservice.service;

import com.pollution.apiservice.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ApiServiceImpl implements ApiService{

    private RestTemplate restTemplate;

    private String getCountriesUrl = "http://api.airvisual.com/v2/countries?key={apiKey}";
    private String getStatesUrl = "http://api.airvisual.com/v2/states?country={country}&key={apiKey}";
    private String getCitiesUrl = "http://api.airvisual.com/v2/cities?state={state}&country={country}&key={apiKey}";
    private String nearestCityUrl = "http://api.airvisual.com/v2/nearest_city?key={apiKey}";
    private String getCityDataByNameUrl = "http://api.airvisual.com/v2/city?city={city}&state={state}&country={country}&key={apiKey}";

    @Value("${api.key}")
    private String apiKey;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Country getAllCountries() {
        return restTemplate.getForObject(getCountriesUrl,Country.class, apiKey);
    }

    @Override
    public State getAllStates(String country) {
        return restTemplate.getForObject(getStatesUrl, State.class, country, apiKey);
    }

    @Override
    public City getAllCities(String country, String state) {
        return restTemplate.getForObject(getCitiesUrl, City.class,state, country, apiKey);
    }

    @Override
    public NearestCity getNearestCityData() {
        return restTemplate.getForObject(nearestCityUrl, NearestCity.class, apiKey);
    }

    @Override
    public CityAQI getCityDataByCityName(String country, String state, String city) {
        return restTemplate.getForObject(getCityDataByNameUrl, CityAQI.class, city, state, country, apiKey);
    }
}

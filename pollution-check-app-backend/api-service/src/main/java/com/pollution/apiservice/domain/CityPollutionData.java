package com.pollution.apiservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityPollutionData {
    private String city;
    private String state;
    private String country;
    private Location location;
    private Current current;
}

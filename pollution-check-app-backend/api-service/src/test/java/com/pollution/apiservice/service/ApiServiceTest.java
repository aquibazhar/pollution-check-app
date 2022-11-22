package com.pollution.apiservice.service;

import com.pollution.apiservice.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiServiceImpl apiService;





    private String getCountriesUrl = "http://api.airvisual.com/v2/countries?key={apiKey}";
    private String getStatesUrl = "http://api.airvisual.com/v2/states?country={country}&key={apiKey}";
    private String getCitiesUrl = "http://api.airvisual.com/v2/cities?state={state}&country={country}&key={apiKey}";
    private String nearestCityUrl = "http://api.airvisual.com/v2/nearest_city?key={apiKey}";
    private String getCityDataByNameUrl = "http://api.airvisual.com/v2/city?city={city}&state={state}&country={country}&key={apiKey}";
    @Value("${api.key}")
    private String apiKey;


    @BeforeEach
    public void setUp() {
        apiService = new ApiServiceImpl(restTemplate);
    }

    @AfterEach
    public void tearDown() {
        apiService = null;
    }

    @Test
    @DisplayName("test case for getAllCountries method of Service")
    public void testForGetAllCountries() throws Exception {
        when(restTemplate.getForObject(getCountriesUrl,Country.class, apiKey)).thenReturn(new Country());
        Mockito.lenient().when(apiService.getAllCountries()).thenReturn(new Country());
    }

    @Test
    @DisplayName("test case for getAllStates method of Service")
    public void testForGetAllStates() throws Exception {
        when(restTemplate.getForObject(getStatesUrl, State.class, "India", apiKey)).thenReturn(new State());
        Mockito.lenient().when(apiService.getAllStates("India")).thenReturn(new State());
    }

    @Test
    @DisplayName("test case for getAllCities method of Service")
    public void testForGetAllCities() throws Exception {
        when(restTemplate.getForObject(getCitiesUrl, City.class, "Uttar Pradesh", "India", apiKey)).thenReturn(new City());
        Mockito.lenient().when(apiService.getAllCities("India", "Uttar Pradesh")).thenReturn(new City());
    }

    @Test
    @DisplayName("test case for getNearestCityData method of Service")
    public void testForGetNearestCityData() throws Exception {
        when(restTemplate.getForObject(nearestCityUrl, NearestCity.class, apiKey)).thenReturn(new NearestCity());
        Mockito.lenient().when(apiService.getNearestCityData()).thenReturn(new NearestCity());
    }

    @Test
    @DisplayName("test case for getCityDataByCityName method of Service")
    public void testForGetCityDataByCityName() throws Exception {
        when(restTemplate.getForObject(getCityDataByNameUrl, CityAQI.class, "Lucknow", "Uttar Pradesh", "India", apiKey)).thenReturn(new CityAQI());
        Mockito.lenient().when(apiService.getCityDataByCityName("India", "Uttar Pradesh", "Lucknow")).thenReturn(new CityAQI());
    }
}

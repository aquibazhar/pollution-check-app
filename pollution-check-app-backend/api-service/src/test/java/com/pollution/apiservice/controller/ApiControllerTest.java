package com.pollution.apiservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pollution.apiservice.domain.*;
import com.pollution.apiservice.service.ApiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ApiServiceImpl apiService;


    @Value("${api.key}")
    private String apiKey;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    @DisplayName("test case for getCountries method of Controller")
    public void testForGetCountries() throws  Exception{
        when(apiService.getAllCountries()).thenReturn(new Country());
        mockMvc.perform(get("http://localhost:8000/api/v1/countries"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test case for getStates method of Controller")
    public void testForGetStates() throws  Exception{
        when(apiService.getAllStates("India")).thenReturn(new State());
        mockMvc.perform(get("http://localhost:8000/api/v1/states/India"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test case for getCities method of Controller")
    public void testForGetCities() throws  Exception{
        when(apiService.getAllCities("India", "Uttar Pradesh")).thenReturn(new City());
        mockMvc.perform(get("http://localhost:8000/api/v1/cities/India/Uttar Pradesh"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test case for getNearestCityData method of Controller")
    public void testForGetNearestCityData() throws  Exception{
        when(apiService.getNearestCityData()).thenReturn(new NearestCity());
        mockMvc.perform(get("http://localhost:8000/api/v1/nearest"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test case for getCityAqiByName method of Controller")
    public void testForGetCityAqiByName() throws  Exception{
        when(apiService.getCityDataByCityName("India", "Uttar Pradesh", "Lucknow")).thenReturn(new CityAQI());
        mockMvc.perform(get("http://localhost:8000/api/v1/cityData/India/Uttar Pradesh/Lucknow"))
                .andExpect(status().isOk());
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }



}

package com.pollution.watchlistservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pollution.watchlistservice.domain.Watchlist;
import com.pollution.watchlistservice.dto.WatchlistDto;
import com.pollution.watchlistservice.service.WatchlistServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchlistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WatchlistServiceImpl watchlistService;
    private WatchlistDto cityData1;
    private WatchlistDto cityData2;

    @InjectMocks
    private WatchlistController watchlistController;

    @BeforeEach
    public void setUp() {
        cityData1 = new WatchlistDto(1, "aaquibazhar1802@gmail.com", "Lucknow", "Uttar Pradesh", "India", "159", "Unhealthy");
        cityData2 = new WatchlistDto(2, "deepanshu2891@gmail.com", "Kanpur", "Uttar Pradesh", "India", "187", "Unhealthy");

        mockMvc = MockMvcBuilders.standaloneSetup(watchlistController).build();
    }

    @AfterEach
    public void tearDown() {
        cityData1 = null;
        cityData2 = null;
    }

    @Test
    @DisplayName("test case for WatchListController's add method")
    public void givenCityDataToSaveReturnSaveCityDataSuccess() throws Exception {
        when(watchlistService.addToWatchlist(any())).thenReturn(new Watchlist(cityData1));
        mockMvc.perform(post("/api/v3/watchlist/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(cityData1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(watchlistService, times(1)).addToWatchlist(any());

    }

    @Test
    @DisplayName("test case for WatchListController's delete method")
    public void givenCityDataIdThenDeleteCityData() throws Exception {
        mockMvc.perform(delete("/api/v3/watchlist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("test case for WatchListController's update method")
    public void givenCityDataThenUpdateCityData() throws Exception {
        WatchlistDto updatedWatchlistDto = new WatchlistDto(1, "aaquibazhar1802@gmail.com", "Lucknow", "Uttar Pradesh", "India", "100", "Healthy");
        when(watchlistService.updateAqiUS(any())).thenReturn(new Watchlist(cityData1));
        mockMvc.perform(put("/api/v3/watchlist/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonToString(updatedWatchlistDto)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("test case for WatchListController's findAll method")
    public void givenUserEmailThenReturnCityDataListForEmail() throws Exception {
        List<Watchlist> watchlistList = new ArrayList<>(Arrays.asList(new Watchlist(cityData1), new Watchlist(cityData2)));
        Mockito.when(watchlistService.findCityDataByUserEmail(any())).thenReturn(watchlistList);
        mockMvc.perform(get("/api/v3/watchlist/aaquibazhar1802@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

}

package com.pollution.watchlistservice.service;

import com.pollution.watchlistservice.domain.Watchlist;
import com.pollution.watchlistservice.dto.WatchlistDto;
import com.pollution.watchlistservice.exceptions.CityDataAlreadyExistsException;
import com.pollution.watchlistservice.exceptions.CityDataNotFoundException;
import com.pollution.watchlistservice.repository.WatchlistRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {
    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private WatchlistServiceImpl watchlistService;
    private Watchlist cityData1, cityData2;
    List<Watchlist> cityDataList;



    @BeforeEach
    public void setUp(){
        cityData1 = new Watchlist(1, "aaquibazhar1802@gmail.com", "Lucknow", "Uttar Pradesh", "India", "159", "Unhealthy");
        cityData2 = new Watchlist(2, "aaquibazhar1802@gmail.com", "Kanpur", "Uttar Pradesh", "India", "187", "Unhealthy");
        cityDataList = Arrays.asList(cityData1,cityData2);
    }

    @AfterEach
    public void tearDown(){
        cityData1 = null;
        cityData2 = null;
    }

    @Test
    @DisplayName("Test for addToWishlist method in service")
    public void givenDataToSaveThenSaveData(){
        when(watchlistRepository.findByUserEmailAndCityAndState(any(), any(), any())).thenReturn(Optional.ofNullable(null));
        when(watchlistRepository.save(any())).thenReturn(cityData1);
        assertEquals(cityData1,watchlistService.addToWatchlist(cityData1));
        verify(watchlistRepository,times(1)).save(any());
        verify(watchlistRepository,times(1)).findByUserEmailAndCityAndState(any(), any(), any());
    }

    @Test
    @DisplayName("Test for remove method in service")
    public void givenNonExistingIdThenThrowException() throws CityDataNotFoundException {

        assertThrows(CityDataNotFoundException.class,() ->  watchlistService.remove(3));
    }

    @Test
    @DisplayName("Test for findCityDataByUserEmail method in service")
    public void givenUserEmailThenReturnUserWatchlist() {
        when(watchlistRepository.findByUserEmail(any())).thenReturn(cityDataList);
        assertEquals(cityDataList,watchlistService.findCityDataByUserEmail("aaquibazhar1802@gmail.com"));
    }

    @Test
    @DisplayName("Test for updateAqiUS method in service")
    public void givenCityDataThenUpdateCityData() {
        when(watchlistRepository.findById(1)).thenReturn(Optional.of(cityData1));
        Watchlist cityDataFetched = watchlistRepository.findById(1).get();
        cityDataFetched.setAqiUS("100");
        watchlistService.updateAqiUS(cityDataFetched);
        assertEquals(cityDataFetched.getAqiUS(),"100");
    }


}

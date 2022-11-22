package com.pollution.watchlistservice.repository;

import com.pollution.watchlistservice.domain.Watchlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WatchlistRepositoryTest {

    @Autowired
    private WatchlistRepository watchlistRepository;

    private Watchlist cityData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cityData = new Watchlist(1, "aaquibazhar1802@gmail.com", "Lucknow", "Uttar Pradesh", "India", "159", "Unhealthy");
    }

    @AfterEach
    public void tearDown() throws Exception {
        watchlistRepository.deleteAll();
    }

    @Test
    @DisplayName("test for findByUserEmail method in repository")
    public void testRegisterUserSuccess() {
        watchlistRepository.save(cityData);
        List<Watchlist> cityDataList = watchlistRepository.findByUserEmail("aaquibazhar1802@gmail.com");
        assertThat(cityDataList.get(0).getUserEmail(), is(cityData.getUserEmail()));
    }

    @Test
    @DisplayName("test for findByUserEmailAndCityAndState method in repository")
    public void testFindByUserEmailAndCityAndState() {
        watchlistRepository.save(cityData);
        Watchlist cityData = watchlistRepository.findByUserEmailAndCityAndState("aaquibazhar1802@gmail.com", "Lucknow", "Uttar Pradesh").get();
        assertThat(cityData.getUserEmail(), is(this.cityData.getUserEmail()));
    }


}

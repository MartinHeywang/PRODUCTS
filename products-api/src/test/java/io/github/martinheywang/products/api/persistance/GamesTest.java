package io.github.martinheywang.products.api.persistance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.exception.RequestException;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

// This class only tests the Games class, not the DataManager 
// (which is fully abstract and only its implementation needs to be tested).
// So here we just check if the Games class called the DataManager
// as intended. The expected results are defined in the 'mock'
// implementation DataManagerTestImpl

@ExtendWith(MockitoExtension.class)
public class GamesTest {

    final Games cut = Games.getSingleton();

    private static DataManager dataManager = mock(DataManager.class);

    @BeforeAll
    public static void beforeAll() throws RequestException {
        try {
            Mockito.doNothing().when(dataManager).setUp();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        DataManager.setDataManager(dataManager);
        final Game game1 = new Game("1");
        game1.setID(1L);
        final Game game2 = new Game("2");
        game2.setID(2L);
        game2.setLastSave(LocalDateTime.now().plusSeconds(3)); // For getMostRecent()
        when(dataManager.getAll(Game.class)).thenReturn(Arrays.asList(game1, game2));
    }

    @Test
    public void getMostRecent_shouldCallTheDataManager_returnsTheLastSavedGame() throws RequestException {
        final Game game = cut.getMostRecent();

        verify(dataManager, times(1)).getAll(Game.class);
        assertThat(game.getName()).isEqualTo("2");
    }
}
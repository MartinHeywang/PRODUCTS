/*
   Copyright 2021 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.persistance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.exception.RequestException;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RequestTest {

    // NOTE : I use the Game class to test the requests
    // That's why I'm using it a lot
    // Its contructor Game(String) is short (practical)

    final Request cut = Request.getSingleton();

    private static DataManager dataManager = mock(DataManager.class);

    @BeforeAll
    public static void beforeAll() throws RequestException {
        try {
            Mockito.doNothing().when(dataManager).setUp();
            Mockito.doNothing().when(dataManager).create(any());
            Mockito.doNothing().when(dataManager).update(any());
            Mockito.doNothing().when(dataManager).delete(any());
        } catch (Exception e) {
            throw new IllegalStateException("Could not initialize mock properly.", e);
        }
        DataManager.setDataManager(dataManager);
        final Game game1 = new Game("1");
        game1.setID(1L);
        final Game game2 = new Game("2");
        game2.setID(2L);
        when(dataManager.getAll(Game.class)).thenReturn(Arrays.asList(game1, game2));
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.clearInvocations(dataManager);
    }

    @Test
    public void countOf_returnsTwo_forTwoPersistedObjects() throws RequestException {
        final int count = cut.countOf(Game.class);

        verify(dataManager, times(1)).getAll(Game.class);
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void getByID_returnsAGame_forIDOne() throws RequestException {
        final Game game = cut.getByID(Game.class, 1);

        verify(dataManager, times(1)).getAll(Game.class);
        assertThat(game.getID()).isEqualTo(1);
    }

    @Test
    public void getByID_returnsAGame_forIDThousand() throws RequestException {
        final Game game = cut.getByID(Game.class, 1000);

        verify(dataManager, times(1)).getAll(Game.class);
        assertThat(game).isEqualTo(null);
    }

    @Test
    public void idExists_returnsTrue_ofIDOne() throws RequestException {
        final boolean exists = cut.idExists(Game.class, 1);

        verify(dataManager, times(1)).getAll(Game.class);
        assertThat(exists).isEqualTo(true);
    }

    @Test
    public void create_shouldCallTheDataManager_forCreate() throws RequestException {
        cut.create(any());

        verify(dataManager, times(1)).create(any());
    }

    @Test
    public void createIfNotExists_shouldCallTheDataManager_forCreate() throws RequestException {
        cut.createIfNotExists(new Game("any"));

        verify(dataManager, times(1)).create(any());
    }

    @Test
    public void createIfNotExists_doNothing() throws RequestException {
        final Game game = new Game("any");
        game.setID(1L); // This id already exists
        cut.createIfNotExists(game);

        verify(dataManager, never()).create(any());
    }

    @Test
    public void createOrUpdate_shouldCallTheDataManager_forCreate_withGameIDNull() throws RequestException {
        cut.createOrUpdate(new Game("any")); // with null ID

        verify(dataManager, times(1)).create(any());
        verify(dataManager, never()).update(any());
    }

    @Test
    public void createOrUpdate_shouldCallTheDataManager_forCreate_withGameIDNotExisting() throws RequestException {
        final Game game = new Game("any");
        game.setID(1000L); // ID not existing
        cut.createOrUpdate(game);

        verify(dataManager, times(1)).create(any());
        verify(dataManager, never()).update(any());
    }

    @Test
    public void createOrUpdate_shouldCallTheDataManager_forUpdate_withGameIDExisting() throws RequestException {
        final Game game = new Game("any");
        game.setID(1L); // ID existing
        cut.createOrUpdate(game);

        verify(dataManager, never()).create(any());
        verify(dataManager, times(1)).update(any());
    }

    @Test
    public void update_shouldCallTheDataManager_forUpdate() throws RequestException {
        cut.update(any());

        verify(dataManager, times(1)).update(any());
    }

    @Test
    public void delete_shouldCallTheDataManager_forDelete() throws RequestException {
        cut.delete(any());

        verify(dataManager, times(1)).delete(any());
    }

    @Test
    public void deleteIfExists_shouldCallTheDataManager_forDelete() throws RequestException {
        final Game game = new Game("any");
        game.setID(1L); // ID exists
        cut.deleteIfExists(game);

        verify(dataManager, times(1)).delete(any());
    }

    @Test
    public void deleteIfExists_doNothing() throws RequestException {
        cut.deleteIfExists(new Game("any"));

        verify(dataManager, never()).delete(any());
    }
}

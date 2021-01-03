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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.exception.RequestException;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoordinatesTest {

    final Coordinates cut = Coordinates.getSingleton();

    private static DataManager dataManager = mock(DataManager.class);

    @BeforeAll
    public static void beforeAll() throws RequestException {
        try {
            Mockito.doNothing().when(dataManager).setUp();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        DataManager.setDataManager(dataManager);
        when(dataManager.getAll(Coordinate.class)).thenReturn(Arrays.asList(new Coordinate(36, 42)));
    }

    @Test
    public void forArgs_shouldCallTheDataManager_returnTheSameValues() throws RequestException {
        final Coordinate coord = cut.forArgs(36, 42);

        verify(dataManager, times(1)).getAll(Coordinate.class);
        assertThat(coord.getX()).isEqualTo(36);
        assertThat(coord.getY()).isEqualTo(42);
    }
}
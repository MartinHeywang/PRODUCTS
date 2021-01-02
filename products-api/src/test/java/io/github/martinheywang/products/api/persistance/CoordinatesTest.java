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
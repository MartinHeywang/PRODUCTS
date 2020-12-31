package io.github.martinheywang.products.api.persistance;

import org.junit.jupiter.api.Test;

public class GamesTest {

    Games games = Games.getSingleton();

    @Test
    public void getAll_shouldCallTheSuperclass(){
        System.out.println(games.getAll());
    }
}
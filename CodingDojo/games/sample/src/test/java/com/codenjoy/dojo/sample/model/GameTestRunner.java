package com.codenjoy.dojo.sample.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.sample.model.level.Level;
import com.codenjoy.dojo.sample.services.Events;
import com.codenjoy.dojo.sample.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.multiplayer.Single;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.LevelUtils;
import com.codenjoy.dojo.utils.TestUtils;
import org.mockito.stubbing.OngoingStubbing;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.sample.services.GameSettings.Keys.LEVEL_MAP;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GameTestRunner {

    private List<EventListener> listeners = new LinkedList<>();
    private List<Game> games = new LinkedList<>();
    private List<Player> players = new LinkedList<>();
    private Dice dice = mock(Dice.class);
    private Sample field;
    private PrinterFactory printer = new PrinterFactoryImpl();
    private GameSettings settings = new GameSettings();

    public void dice(int...ints) {
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    public void givenFl(String map) {
        settings.string(LEVEL_MAP, map);

        Level level = settings.level();
        field = new Sample(level, dice, settings);
        level.heroes()
                .forEach(hero -> givenPlayer(hero.getX(), hero.getY()));
    }

    public Player givenPlayer(int x, int y) {
        EventListener listener = mock(EventListener.class);
        listeners.add(listener);
        Player player = new Player(listener, settings);
        players.add(player);
        Game game = new Single(player, printer);
        games.add(game);
        dice(x, y);
        game.on(field);
        game.newGame();
        return player;
    }

    public void tick() {
        field.tick();
    }

    public void newGame() {
        checkSingleplayer();

        newGame(0);
    }

    public void newGame(int index) {
        field.newGame(player(index));
    }

    public void event(Events event) {
        checkSingleplayer();
        event(0, event);
    }

    public void event(int index, Events event) {
        verify(listeners.get(index)).event(event);
    }

    public Game game(int index) {
        return games.get(index);
    }

    public Hero hero() {
        checkSingleplayer();
        return hero(0);
    }

    public Hero hero(int index) {
        return players.get(index).getHero();
    }

    public void assertE(String expected, int index) {
        assertEquals(TestUtils.injectN(LevelUtils.clear(expected)), field(index));
    }

    public void assertE(String expected) {
        checkSingleplayer();
        assertE(expected, 0);
    }

    public void checkSingleplayer() {
        if (games.size() != 1) {
            throw new UnsupportedOperationException("Please call this method only for single player game");
        }
    }

    public String field(int index) {
        return (String) printer.getPrinter(field.reader(), player(index)).print();
    }

    private Player player(int index) {
        return players.get(index);
    }

}
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


import com.codenjoy.dojo.sample.services.Events;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MultiplayerTest extends GameTestRunner {

    public void givenThreePlayers() {
        givenPlayer(1, 4);
        givenPlayer(2, 2);
        givenPlayer(3, 4);
    }

    // рисуем несколько игроков
    @Test
    public void shouldPrint() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼   $☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        // when then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertE("☼☼☼☼☼☼\n" +
                "☼☻ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertE("☼☼☼☼☼☼\n" +
                "☼☻ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);
    }

    // Каждый игрок может упраыляться за тик игры независимо
    @Test
    public void shouldJoystick() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        // when
        game(0).getJoystick().act();
        game(0).getJoystick().down();
        game(1).getJoystick().right();
        game(2).getJoystick().down();

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼x   ☼\n" +
                "☼☺ ☻ ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // игроков можно удалять из игры
    @Test
    public void shouldRemove() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        // when
        game(2).close();

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // игрок может взорваться на бомбе
    @Test
    public void shouldKill() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        game(0).getJoystick().down();
        game(0).getJoystick().act();
        game(2).getJoystick().left();

        tick();

        assertE("☼☼☼☼☼☼\n" +
                "☼x☻  ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
        
        // when
        game(2).getJoystick().left();
        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼X   ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        event(2, Events.LOOSE);
        assertTrue(game(2).isGameOver());

        dice(4, 1);
        game(2).newGame();

        tick();

        assertE("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ☻☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // игрок может подобрать золото
    @Test
    public void shouldGetGold() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼   $☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        // when
        game(2).getJoystick().right();

        dice(1, 2);

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺  ☻☼\n" +
                "☼    ☼\n" +
                "☼$☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        event(2, Events.WIN);
    }

    // игрок не может пойи на другого игрока
    @Test
    public void shouldCantGoOnHero() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        givenThreePlayers();

        // when
        game(0).getJoystick().right();
        game(2).getJoystick().left();

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼ ☺☻ ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }
}

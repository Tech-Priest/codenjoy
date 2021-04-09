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

    // several heroes can appear on the map
    @Test
    public void severalHeroesCanAppearOnTheMap() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertE("☼☼☼☼☼☼\n" +
                "☼☻ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertE("☼☼☼☼☼☼\n" +
                "☼☻ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);
    }

    // each hero can be controlled independently in one tick of the game
    @Test
    public void eachHeroCanBeControlledIndependentlyInOneTickOfTheGame() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        game(0).getJoystick().act();
        game(0).getJoystick().down();
        game(1).getJoystick().down();
        game(2).getJoystick().right();

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼x   ☼\n" +
                "☼☺ ☻ ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // heroes can be removed from the game
    @Test
    public void heroesCanBeRemovedFromTheGame() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        game(1).close();

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // any of the heroes can explode on a bomb
    @Test
    public void anyOfTheHeroesCanExplodeOnABomb() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        game(0).getJoystick().down();
        game(0).getJoystick().act();
        game(1).getJoystick().left();

        tick();

        assertE("☼☼☼☼☼☼\n" +
                "☼x☻  ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        // when
        game(1).getJoystick().left();
        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼X   ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        event(1, Events.LOOSE);
        assertTrue(game(1).isGameOver());

        dice(4, 1);
        game(1).newGame();

        tick();

        assertE("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ☻☼\n" +
                "☼☼☼☼☼☼\n", 0);
    }

    // any of the heroes can pick up gold
    @Test
    public void anyOfTheHeroesCanPickUpGold() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        game(1).getJoystick().right();

        dice(1, 2);

        tick();

        // then
        assertE("☼☼☼☼☼☼\n" +
                "☼☺  ☻☼\n" +
                "☼    ☼\n" +
                "☼$☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        event(1, Events.WIN);
    }

    // heroes cannot walk through one another
    @Test
    public void heroesCannotWalkThroughOneAnother() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        game(0).getJoystick().right();
        game(1).getJoystick().left();

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
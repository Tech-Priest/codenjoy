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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class GameTest extends GameTestRunner {

    // есть карта со мной
    @Test
    public void shouldFieldAtStart() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я ходить
    @Test
    public void shouldWalk() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).left();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).right();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).up();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).down();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }
    // если небыло команды я никуда не иду
    @Test
    public void shouldStopWhenNoMoreRightCommand() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ☺☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).left();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я останавливаюсь возле границы
    @Test
    public void shouldStopWhenWallRight() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ☺☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).right();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼  ☺☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldStopWhenWallLeft() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).left();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldStopWhenWallUp() {
        givenFl("☼☼☼☼☼\n" +
                "☼ ☼ ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).up();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼ ☼ ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void shouldStopWhenWallDown() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼ ☼ ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).down();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼ ☼ ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу оставить бомбу
    @Test
    public void shouldMakeBomb() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).act();
        hero(0).down();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x ☼\n" +
                "☼ ☺ ☼\n" +
                "☼☼☼☼☼\n");
    }

    // на бомбе я взрываюсь
    @Test
    public void shouldDieOnBomb() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x ☼\n" +
                "☼ ☺ ☼\n" +
                "☼☼☼☼☼\n");

        assertTrue(hero(0).isAlive());

        hero(0).up();
        tick();
        event(0, Events.LOOSE);

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ X ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        assertFalse(hero(0).isAlive());
    }

    // я могу оставить бомб сколько хочу
    @Test
    public void shouldMakeBombTwice() {
        shouldMakeBomb();

        hero(0).act();
        hero(0).right();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x ☼\n" +
                "☼ x☺☼\n" +
                "☼☼☼☼☼\n");
    }

    // я могу собирать золото и получать очки
    // новое золото появится в рендомном месте
    @Test
    public void shouldGetGold() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺$☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        dice(1, 3);
        hero(0).right();
        tick();
        event(0, Events.WIN);

        assertE("☼☼☼☼☼\n" +
                "☼$  ☼\n" +
                "☼  ☺☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    // выполнения команд left + act не зависят от порядка - если они сделаны в одном тике, то будет дырка слева без перемещения
    @Test
    public void shouldMakeBomb2() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).down();
        hero(0).act();
//        hero(0).down();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x ☼\n" +
                "☼ ☺ ☼\n" +
                "☼☼☼☼☼\n");
    }

    // проверить, что если новому обекту не где появится то программа не зависает - там бесконечный цикл потенциальный есть
    @Test(timeout = 1000)
    public void shouldNoDeadLoopWhenNewObjectCreation() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺$☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        dice(2, 2);
        hero(0).right();
        tick();
        event(0, Events.WIN);

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ $☺☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    // я не могу ставить две бомбы на одной клетке
    @Test
    public void shouldMakeOnlyOneBomb() {
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        hero(0).act();
        tick();

        hero(0).act();
        hero(0).down();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ x ☼\n" +
                "☼ ☺ ☼\n" +
                "☼☼☼☼☼\n");

        dice(1, 2);
        hero(0).up();
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ X ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        newGame(0);
        tick();

        assertE("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }
}

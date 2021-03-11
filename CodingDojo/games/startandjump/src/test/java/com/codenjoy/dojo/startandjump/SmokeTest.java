package com.codenjoy.dojo.startandjump;

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


import com.codenjoy.dojo.client.local.LocalGameRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.startandjump.client.Board;
import com.codenjoy.dojo.startandjump.client.ai.AISolver;
import com.codenjoy.dojo.startandjump.services.GameRunner;
import com.codenjoy.dojo.startandjump.services.GameSettings;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.startandjump.services.GameSettings.Keys.LEVEL_MAP;
import static org.junit.Assert.assertEquals;

public class SmokeTest {

    @Test
    public void test() {
        // given
        List<String> messages = new LinkedList<>();

        LocalGameRunner.timeout = 0;
        LocalGameRunner.out = (e) -> messages.add(e);
        LocalGameRunner.countIterations = 10;

        Dice dice = LocalGameRunner.getDice(
                0, 2, 4, 1, 2, // random numbers
                0, 3, 5, 6, 6,
                0, 4, 6, 1, 3,
                0, 4, 7, 6, 6,
                0, 4, 5, 6, 3,
                2, 1, 4, 0, 2,
                3, 5, 4, 6, 1,
                2, 1, 5, 3, 2,
                0, 1, 3, 2, 1);

        GameRunner gameType = new GameRunner() {
            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                return super.getSettings()
                        .string(LEVEL_MAP,
                            "#########" +
                            " =       " +
                            " =       " +
                            " =       " +
                            "         " +
                            "☺       =" +
                            " =    == " +
                            " =  ==   " +
                            "#########");
            }
        };

        // when
        LocalGameRunner.run(gameType,
                new AISolver(dice),
                new Board());

        // then
        assertEquals("1:Board:\n" +
                        "1:#########\n" +
                        "1: =       \n" +
                        "1: =       \n" +
                        "1: =       \n" +
                        "1:         \n" +
                        "1:☺       =\n" +
                        "1: =    == \n" +
                        "1: =  ==   \n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 0\n" +
                        "1:Answer: UP\n" +
                        "DICE:0\n" +
                        "DICE:2\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:=        \n" +
                        "1:=        \n" +
                        "1:=        \n" +
                        "1:☺        \n" +
                        "1:       = \n" +
                        "1:=    ==  \n" +
                        "1:=  ==   =\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 30\n" +
                        "1:Answer: UP\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:         \n" +
                        "1:      =  \n" +
                        "1:    ==   \n" +
                        "1:  ==   ==\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 60\n" +
                        "1:Answer: UP\n" +
                        "DICE:4\n" +
                        "DICE:1\n" +
                        "DICE:2\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:     =   \n" +
                        "1:   ==    \n" +
                        "1: ==   == \n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 90\n" +
                        "1:Answer: UP\n" +
                        "DICE:0\n" +
                        "DICE:3\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺   =    \n" +
                        "1:  ==     \n" +
                        "1:==   == =\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 120\n" +
                        "1:Answer: UP\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺  =     \n" +
                        "1: ==      \n" +
                        "1:=   == ==\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 150\n" +
                        "1:Answer: UP\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:  =      \n" +
                        "1:==       \n" +
                        "1:   == ===\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 180\n" +
                        "1:Answer: UP\n" +
                        "DICE:5\n" +
                        "DICE:6\n" +
                        "DICE:6\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:         \n" +
                        "1: =       \n" +
                        "1:=        \n" +
                        "1:  == === \n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 210\n" +
                        "1:Answer: UP\n" +
                        "DICE:0\n" +
                        "DICE:4\n" +
                        "DICE_CORRECTED < 4 :0\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:=        \n" +
                        "1:         \n" +
                        "1: == ===  \n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 240\n" +
                        "1:Answer: UP\n" +
                        "DICE:6\n" +
                        "DICE_CORRECTED < 6 :0\n" +
                        "DICE:1\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------\n" +
                        "1:Board:\n" +
                        "1:#########\n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:☺        \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:         \n" +
                        "1:== ===  =\n" +
                        "1:#########\n" +
                        "1:\n" +
                        "1:Scores: 270\n" +
                        "1:Answer: UP\n" +
                        "DICE:3\n" +
                        "DICE:0\n" +
                        "DICE:4\n" +
                        "1:Fire Event: STILL_ALIVE\n" +
                        "------------------------------------------",
                String.join("\n", messages));

    }
}

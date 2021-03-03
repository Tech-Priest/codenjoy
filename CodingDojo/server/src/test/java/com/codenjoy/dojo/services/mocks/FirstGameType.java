package com.codenjoy.dojo.services.mocks;

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


import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.MultiplayerType;
import com.codenjoy.dojo.services.printer.CharElements;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SimpleParameter;

import static com.codenjoy.dojo.services.PointImpl.pt;

public class FirstGameType extends FakeGameType {

    @Override
    public Parameter<Integer> getBoardSize(Settings settings) {
        return new SimpleParameter<>(10);
    }

    @Override
    public SettingsImpl getSettings() {
        return new FirstGameSettings();
    }

    @Override
    public String name() {
        return "first";
    }

    public enum Elements implements CharElements {

        NONE(' '),
        WALL('☼'),
        HERO('☺');

        final char ch;

        Elements(char ch) {
            this.ch = ch;
        }

        @Override
        public char ch() {
            return ch;
        }

        @Override
        public String toString() {
            return String.valueOf(ch);
        }

    }

    @Override
    public Elements[] getPlots() {
        return Elements.values();
    }

    @Override
    public MultiplayerType getMultiplayerType(Settings settings) {
        return MultiplayerType.SINGLE;
    }

    @Override
    public Point heroAt() {
        return pt(1, 1);
    }

    @Override
    public CharElements getHeroElement() {
        return Elements.HERO;
    }

    @Override
    public String getVersion() {
        return "version 1.11b";
    }
}

package com.codenjoy.dojo.services.nullobj;

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

import com.codenjoy.dojo.services.multiplayer.GameField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.settings.SettingsReader;

public final class NullGameField implements GameField {

    public static final GameField INSTANCE = new NullGameField();

    private NullGameField() {
        // do nothing
    }

    @Override
    public BoardReader reader() {
        return NullBoardReader.INSTANCE;
    }

    @Override
    public void newGame(GamePlayer player) {
        // do nothing
    }

    @Override
    public void remove(GamePlayer player) {
        // do nothing
    }

    @Override
    public SettingsReader settings() {
        return null;
    }

    @Override
    public void tick() {
        // do nothing
    }
}

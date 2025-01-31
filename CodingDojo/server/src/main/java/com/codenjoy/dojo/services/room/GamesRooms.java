package com.codenjoy.dojo.services.room;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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

import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class GamesRooms {

    private final List<GameRooms> all;

    public List<String> getGames() {
        return all.stream()
                .map(gameRooms -> gameRooms.getGame())
                .collect(toList());
    }

    public List<String> getRooms() {
        return all.stream()
                .flatMap(gameRooms -> gameRooms.getRooms().stream())
                .collect(toList());
    }

    public List<String> getRooms(String game) {
        return all.stream()
                .filter(gameRooms -> gameRooms.getGame().equals(game))
                .flatMap(gameRooms -> gameRooms.getRooms().stream())
                .collect(toList());
    }

    @Override
    public String toString() {
        return all.toString();
    }
}

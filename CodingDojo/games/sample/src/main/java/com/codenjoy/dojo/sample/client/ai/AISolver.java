package com.codenjoy.dojo.sample.client.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.sample.client.Board;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.sample.model.Elements.GOLD;

/**
 * Это алгоритм твоего бота. Он будет запускаться в игру с первым
 * зарегистрировавшимся игроком, чтобы ему не было скучно играть самому.
 * Реализуй его как хочешь, хоть на Random (только используй для этого
 * {@see Dice} что приходит через конструктор).
 * Для его запуска воспользуйся методом {@see ApofigSolver#main}
 */
public class AISolver implements Solver<Board> {

    private static final int ACT_EVERY = 10;

    private DeikstraFindWay way;
    private Dice dice;

    public AISolver(Dice dice) {
        this.dice = dice;
        this.way = new DeikstraFindWay();
    }

    public DeikstraFindWay.Possible possible(Board board, boolean ignoreBombs) {
        return new DeikstraFindWay.Possible() {
            @Override
            public boolean possible(Point pt) {
                int x = pt.getX();
                int y = pt.getY();
                if (board.isBarrierAt(x, y)) return false;
                if (!ignoreBombs && board.isBombAt(x, y)) return false;
                return true;
            }
        };
    }

    @Override
    public String get(Board board) {
        if (board.isGameOver()) return "";
        List<Direction> result = getDirections(board);
        if (result.isEmpty()) {
            return Direction.random(dice).toString();
        }
        Direction direction = result.get(0);
        // бомбу ставим каждый ACT_EVERY ход
        if (dice.next(ACT_EVERY) % ACT_EVERY == 0) {
            return direction.ACT(true);
        } else {
            return direction.toString();
        }
    }

    public List<Direction> getDirections(Board board) {
        int size = board.size();

        Point from = board.getMe();
        List<Point> to = board.get(GOLD);
        if (to.isEmpty()) {
            return Arrays.asList();
        }
        List<Direction> result = way.getShortestWay(size, from, to, possible(board, false));
        if (result.isEmpty()) {
            // если к золоту не можем построить маршрут мимо бомб, идем напролом
            result = way.getShortestWay(size, from, to, possible(board, true));
        }
        return result;
    }
}
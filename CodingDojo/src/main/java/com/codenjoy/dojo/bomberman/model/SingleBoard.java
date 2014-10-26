package com.codenjoy.dojo.bomberman.model;

import com.codenjoy.dojo.rubicscube.model.Element;
import com.codenjoy.dojo.services.*;

import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 16.04.13
 * Time: 21:43
 */
public class SingleBoard implements Game {

    private Player player;
    private Board board;

    private Printer printer;

    public SingleBoard(final Board board, EventListener listener) {
        this.board = board;
        player = new Player(listener);

        printer = new Printer(board.size(),
                new Printer.GamePrinterImpl<Elements, Player>(board.reader(), player, Elements.EMPTY.ch()));
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Joystick getJoystick() {
        return player.getBomberman();
    }

    @Override
    public int getMaxScore() {
        return player.getMaxScore();
    }

    @Override
    public int getCurrentScore() {
        return player.getScore();
    }

    @Override
    public boolean isGameOver() {
        return !player.getBomberman().isAlive();
    }

    @Override
    public void newGame() {
        board.newGame(player);
    }

    @Override
    public String getBoardAsString() {
        return printer.toString();
    }

    @Override
    public void destroy() {
        board.remove(player);
    }

    @Override
    public void clearScore() {
        player.clearScore();
    }

    @Override
    public Point getHero() {
        return player.getBomberman();
    }

    @Override
    public void tick() {
        board.tick();
    }

}

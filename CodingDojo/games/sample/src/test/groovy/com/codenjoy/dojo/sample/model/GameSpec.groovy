package com.codenjoy.dojo.sample.model

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

import static com.codenjoy.dojo.sample.services.Events.*
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class GameSpec extends Specification {

    GameTestRunner game = new GameTestRunner()
    boolean given = true

    void field(String field, int index = 0) {
        if (given) {
            game.givenFl(c(field))
            given = false
        } else {
            assert game.field(index) == c(field)
        }
    }

    static String c(String field) {
        int len = field.length() - StringUtils.stripStart(field, null).length()
        return field.replaceAll("\n" + (" ".repeat(len - 1)), "\n").replaceFirst("\n", "")
    }

    static {
        GameTestRunner.metaClass.next = { ->
            delegate.tick()
            return delegate
        }
        GameTestRunner.metaClass.getHero = { index = 0 ->
            return delegate.hero(index)
        }
        GameTestRunner.metaClass.hero = { index = 0, ch ->
            switch (ch) {
                case '>' : delegate.hero(index).right(); break
                case '<' : delegate.hero(index).left(); break
                case '˄' : delegate.hero(index).up(); break
                case '˅' : delegate.hero(index).down(); break
                case '*' : delegate.hero(index).act(); break
            }
            return delegate
        }
    }

    def "hero on the field"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can walk to the left"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '<'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can walk to the right"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '>'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can walk to the up"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˄'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼ ☺ ☼
            ☼   ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can walk to the down"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˅'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼   ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''
    }

    def "if the hero does not receive commands, he does not go anywhere"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        game.hero '<'
        game++

        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero cannot go through the border to the left"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '<'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero cannot go through the border to the right"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '>'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero cannot go through the border to the up"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˄'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero cannot go through the border to the down"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˅'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can leave the bomb under him"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '*'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˅'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can leave the bomb under him and at the same moment move to the side"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '*'
        game.hero '˅'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''
    }

    def "there is no difference in what order the movement and the act command are executed"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˅' // different order than in the previous test
        game.hero '*'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''
    }

    def "hero will blow up on a bomb if he walks on it"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''

        assert game.hero.alive == true

        when:
        game.hero '˄'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ X ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
        game.event LOOSE
        game.hero.alive == false
    }

    def "hero on the field can leave as many bombs as he wants"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '˅'
        game.hero '*'
        game++

        game.hero '>'
        game.hero '*'
        game++

        then:
        field '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ x☺☼
            ☼☼☼☼☼
            '''
    }

    def "hero cannot leave two bombs in one cell"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero '*'
        game++

        game.hero '*'
        game.hero '˅'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice 1, 2
        game.hero '˄'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ X ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.newGame()
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }

    def "hero can pick up gold on the map, after which it will appear in a new place"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice 1, 3
        game.hero '>'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼$  ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''
        game.event WIN
    }

    def "if there is no place for gold, then the program does not freeze"() {
        given:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice 0, 0  // there is no space in this cell because of board
        game.hero '>'
        game++

        then:
        field'''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''
        game.event WIN
    }

    // TODO добавить мультиплеер тесты тут
}

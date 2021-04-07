package com.codenjoy.dojo.sample.model

import static com.codenjoy.dojo.sample.services.Events.*
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()
    boolean given = true

    void field(String field) {
        if (given) {
            game.givenFl(c(field).replace("\n", ""))
            given = false
        } else {
            assert game.field() == c(field)
        }
    }

    static String c(String field) {
        int len = field.length() - StringUtils.stripStart(field, null).length()
        return field.replaceAll("\n" + (" ".repeat(len - 1)), "\n").replaceFirst("\n", "")
    }

    static {
        AbstractGameTest.metaClass.next = { ->
            delegate.tick()
            return delegate
        }
        AbstractGameTest.metaClass.hero = { ch ->
            switch (ch) {
                case '>' : delegate.hero.right(); break
                case '<' : delegate.hero.left(); break
                case '˄' : delegate.hero.up(); break
                case '˅' : delegate.hero.down(); break
                case '*' : delegate.hero.act(); break
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
}
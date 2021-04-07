package com.codenjoy.dojo.sample.model

import com.codenjoy.dojo.sample.services.Events
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

import static org.mockito.Mockito.verify

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()

    void check(String field) {
        assert game.field() == c(field)
    }

    void field(String field) {
        game.givenFl(c(field).replace("\n", ""))
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
        check'''
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
        game.hero.left()
        game++

        then:
        check'''
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
        game.hero.right()
        game++

        then:
        check'''
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
        game.hero.up()
        game++

        then:
        check'''
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
        game.hero.down()
        game++

        then:
        check'''
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

        game.hero.left()
        game++

        check'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game++

        then:
        check'''
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
        game.hero.left()
        game++

        then:
        check'''
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
        game.hero.right()
        game++

        then:
        check'''
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
        game.hero.up()
        game++

        then:
        check'''
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
        game.hero.down()
        game++

        then:
        check'''
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
        game.hero.act()
        game++

        then:
        check'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.down()
        game++

        then:
        check'''
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
        game.hero.act()
        game.hero.down()
        game++

        then:
        check'''
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
        game.hero.down() // different order than in the previous test
        game.hero.act()
        game++

        then:
        check'''
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
        game.hero.up()
        game++

        then:
        verify(game.listener).event(Events.LOOSE)
        check'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ X ☼
            ☼   ☼
            ☼☼☼☼☼
            '''
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
        game.hero.down()
        game.hero.act()
        game++

        game.hero.right()
        game.hero.act()
        game++

        then:
        check '''
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
        game.hero.act()
        game++

        game.hero.act()
        game.hero.down()
        game++

        then:
        check'''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice(1, 2)
        game.hero.up()
        game++

        then:
        check'''
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
        check'''
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
        game.dice(1, 3)
        game.hero.right()
        game++

        then:
        verify(game.listener).event(Events.WIN)
        check'''
            ☼☼☼☼☼
            ☼$  ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''
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
        game.dice(0, 0)  // there is no space in this cell because of board
        game.hero.right()
        game++

        then:
        verify(game.listener).event(Events.WIN)
        check'''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''
    }
}
package com.codenjoy.dojo.sample.model

import com.codenjoy.dojo.sample.services.Events
import spock.lang.Specification

import static com.codenjoy.dojo.sample.model.AbstractGameTest.c
import static org.mockito.Mockito.verify;

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()

    def "hero on the field"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the left"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.left()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the right"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.right()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the up"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.up()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼ ☺ ☼
            ☼   ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the down"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.down()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼   ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "if the hero does not receive commands, he does not go anywhere"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        game.hero.left();
        game.tick();

        assert game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the left"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.left()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the right"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.right()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the up"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.up()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the down"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.down()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can leave the bomb under him"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.act()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.down()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can leave the bomb under him and at the same moment move to the side"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.act()
        game.hero.down()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "there is no difference in what order the movement and the act command are executed" () {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.down() // different order than in the previous test
        game.hero.act()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero will blow up on a bomb if he walks on it"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')

        assert game.hero.alive == true

        when:
        game.hero.up()
        game.tick()

        then:
        verify(game.listener).event(Events.LOOSE);
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ X ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
        game.hero.alive == false
    }

    def "hero on the field can leave as many bombs as he wants"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.down()
        game.hero.act()
        game.tick()

        game.hero.right()
        game.hero.act()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ x☺☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot leave two bombs in one cell"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.hero.act()
        game.tick()

        game.hero.act()
        game.hero.down()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')

        when:
        game.dice(1, 2)
        game.hero.up()
        game.tick()

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ X ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.newGame()
        game.tick();

        then:
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can pick up gold on the map, after which it will appear in a new place"() {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.dice(1, 3)
        game.hero.right()
        game.tick()

        then:
        verify(game.listener).event(Events.WIN);
        game.field() == c('''
            ☼☼☼☼☼
            ☼$  ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "if there is no place for gold, then the program does not freeze" () {
        given:
        game << c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.dice(0, 0)  // there is no space in this cell because of board
        game.hero.right()
        game.tick()

        then:
        verify(game.listener).event(Events.WIN);
        game.field() == c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }
}
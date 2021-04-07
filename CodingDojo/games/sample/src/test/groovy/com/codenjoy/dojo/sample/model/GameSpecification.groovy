package com.codenjoy.dojo.sample.model

import com.codenjoy.dojo.sample.services.Events
import spock.lang.Specification

import static com.codenjoy.dojo.sample.model.AbstractGameTest.c
import static org.mockito.Mockito.verify;

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()

    void check(String board) {
        assert game.field() == c(board)
    }

    def "hero on the field"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the left"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.left()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the right"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.right()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the up"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.up()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼ ☺ ☼
            ☼   ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can walk to the down"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.down()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼   ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "if the hero does not receive commands, he does not go anywhere"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        game.hero.left();
        game.tick();

        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')

        when:
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the left"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.left()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☼☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the right"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.right()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺☼☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the up"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.up()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼ ☼ ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot go through the border to the down"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.down()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼ ☼ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can leave the bomb under him"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.act()
        game.tick()

        then:
        check('''
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
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can leave the bomb under him and at the same moment move to the side"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.act()
        game.hero.down()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "there is no difference in what order the movement and the act command are executed"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.down() // different order than in the previous test
        game.hero.act()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero will blow up on a bomb if he walks on it"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ ☺ ☼
            ☼☼☼☼☼
            '''

        assert game.hero.alive == true

        when:
        game.hero.up()
        game.tick()

        then:
        verify(game.listener).event(Events.LOOSE);
        check('''
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
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.down()
        game.hero.act()
        game.tick()

        game.hero.right()
        game.hero.act()
        game.tick()

        then:
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ x ☼
            ☼ x☺☼
            ☼☼☼☼☼
            ''')
    }

    def "hero cannot leave two bombs in one cell"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.hero.act()
        game.tick()

        game.hero.act()
        game.hero.down()
        game.tick()

        then:
        check('''
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
        check('''
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
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼☺  ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "hero can pick up gold on the map, after which it will appear in a new place"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice(1, 3)
        game.hero.right()
        game.tick()

        then:
        verify(game.listener).event(Events.WIN);
        check('''
            ☼☼☼☼☼
            ☼$  ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }

    def "if there is no place for gold, then the program does not freeze"() {
        given:
        game << '''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺$☼
            ☼   ☼
            ☼☼☼☼☼
            '''

        when:
        game.dice(0, 0)  // there is no space in this cell because of board
        game.hero.right()
        game.tick()

        then:
        verify(game.listener).event(Events.WIN);
        check('''
            ☼☼☼☼☼
            ☼   ☼
            ☼  ☺☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }
}
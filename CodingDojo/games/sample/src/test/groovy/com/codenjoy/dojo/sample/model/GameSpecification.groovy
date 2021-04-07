package com.codenjoy.dojo.sample.model


import spock.lang.Specification

import static com.codenjoy.dojo.sample.model.AbstractGameTest.c;

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()

    def "hero on the field"() {
        given:
        game <<
            c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
        when:
        game.tick()

        then:
        game.field() ==
            c('''
            ☼☼☼☼☼
            ☼   ☼
            ☼ ☺ ☼
            ☼   ☼
            ☼☼☼☼☼
            ''')
    }
}

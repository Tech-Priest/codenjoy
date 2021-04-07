package com.codenjoy.dojo.sample.model


import spock.lang.Specification;

class GameSpecification extends Specification {

    AbstractGameTest game = new AbstractGameTest()

    def "hero on the field"() {
        given:
        game <<
'''☼☼☼☼☼
☼   ☼
☼ ☺ ☼
☼   ☼
☼☼☼☼☼'''
        when:
        game.tick()

        then:
        game.board() ==
'''☼☼☼☼☼
☼   ☼
☼ ☺ ☼
☼   ☼
☼☼☼☼☼
'''
    }
}

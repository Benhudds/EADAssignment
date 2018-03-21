/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests.shapes

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*

import shapes.Line
import questions.LineQuestions

class TestLine {

    def line = new Line();
    
    def "perimeter calculation correct len 1"() {
        given:
        line.setX(0)
        line.setY(0)
        line.setX2(1)
        line.setY2(1)
        when:
        double ans = square.getAnswer(SquareQuestions.PERIMETER)
        then:
        ans = 1
    }
    
    def "perimeter calculation correct len x"() {
        given:
        line.setX(2)
        line.setY(0)
        line.setX2(5)
        line.setY2(9)
        when:
        double ans = square.getAnswer(SquareQuestions.PERIMETER)
        then:
        ans = 9.48
    }
}

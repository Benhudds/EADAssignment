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
import shapes.Square
import questions.SquareQuestions

import static org.junit.Assert.*

class TestSquare extends spock.lang.Specification {
    
    def square = new Square();
    
    def "area calculation correct"() {
        given:
        square.setX(0)
        square.setY(0)
        square.size = 5
        when:
        double ans = square.getAnswer(SquareQuestions.AREA)
        then:
        ans == square.size * square.size
    }
    
    def "perimeter calculation correct"() {
        given:
        square.setX(0)
        square.setY(0)
        square.size = 5
        when:
        double ans = square.getAnswer(SquareQuestions.PERIMETER)
        then:
        ans == square.size * 4
    }
}

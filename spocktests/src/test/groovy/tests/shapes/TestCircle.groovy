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
import shapes.Circle
import questions.CircleQuestions

import static org.junit.Assert.*

class TestCircle extends spock.lang.Specification {
    def circle = new Circle();
    
    def "area calculation correct"() {
        given:
        circle.setX(0)
        circle.setY(0)
        circle.radius = 0
        when:
        double ans = circle.getAnswer(CircleQuestions.AREA)
        then:
        ans == Math.PI * circle.getRadius() * circle.getRadius()
    }
    
    def "circumference calculation correct"() {
        given:
        circle.setX(0)
        circle.setY(0)
        circle.radius = 0
        when:
        double ans = circle.getAnswer(CircleQuestions.CIRCUMFERENCE)
        then:
        ans == Math.PI * circle.getRadius() * 2
    }
}

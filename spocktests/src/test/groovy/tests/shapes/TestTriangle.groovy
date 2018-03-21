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
import shapes.Triangle
import questions.TriangleQuestions

class TestTriangle extends spock.lang.Specification {
    def triangle = new Triangle();
    
    def "area calculation correct"() {
        given:
        triangle.x = 2
        triangle.y = 1
        triangle.x2 = 1
        triangle.y2 = 8
        triangle.x3 = 8
        triangle.y3 = 9
        when:
        double ans = triangle.getAnswer(TriangleQuestions.AREA)
        then:
        ans == 25
    }

    
    def "perimeter calculation correct"() {
        given:
        triangle.x = 2
        triangle.y = 1
        triangle.x2 = 1
        triangle.y2 = 8
        triangle.x3 = 8
        triangle.y3 = 9
        when:
        double ans = triangle.getAnswer(TriangleQuestions.PERIMETER)
        then:
        ans == 24.14213562373095
    }
}
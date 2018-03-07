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
import web.shapes.Circle.Circle

import static org.junit.Assert.*

class TestCircle extends spock.lang.Specification {
    def circle = new Circle();
    
    def "area calculation correct"() {
        given:
        circle.setX(0);
        circle.setX(0);
        circle.setRadius(0);
        then:
        circle.getAnswer(CircleQuestions.AREA) == Math.pi * circle.getRadius() * circle.getRadius();
    }
}

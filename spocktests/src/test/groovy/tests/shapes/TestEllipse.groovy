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
import shapes.Ellipse
import questions.EllipseQuestions

class TestEllipse extends spock.lang.Specification{

    def ellipse = new Ellipse();
    
     def "area calculation correct 0"() {
        given:
        ellipse.setX(0)
        ellipse.setY(0)
        ellipse.XRadius = 0
        ellipse.YRadius = 0
        when:
        double ans = ellipse.getAnswer(EllipseQuestions.AREA)
        then:
        ans == 0
    }
    
    def "area calculation correct"() {
        given:
        ellipse.setX(0)
        ellipse.setY(0)
        ellipse.XRadius = 2
        ellipse.YRadius = 8
        when:
        double ans = ellipse.getAnswer(EllipseQuestions.AREA)
        then:
        ans == 50.26548245743669
    }

    
    def "perimeter calculation correct 0"() {
        given:
        ellipse.setX(0)
        ellipse.setY(0)
        ellipse.XRadius = 0
        ellipse.YRadius = 0
        when:
        double ans = ellipse.getAnswer(EllipseQuestions.PERIMETER)
        then:
        ans == 0
    }
    
    def "perimeter calculation correct 2"() {
        given:
        ellipse.setX(0)
        ellipse.setY(0)
        ellipse.XRadius = 2
        ellipse.YRadius = 8
        when:
        double ans = ellipse.getAnswer(EllipseQuestions.PERIMETER)
        then:
        ans == 36.63695127256296
    }
}

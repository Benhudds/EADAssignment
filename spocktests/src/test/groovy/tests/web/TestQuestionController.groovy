/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests.web

import com.JWTHelper
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*
import groovyx.net.http.RESTClient
import groovyx.net.http.ResponseParseException
import static groovyx.net.http.ContentType.JSON

class TestQuestionController extends spock.lang.Specification {
    
    RESTClient client = new RESTClient("http://localhost:8080/")
    
    def "get question unauth"() {
        given: "no user logged in"
        when:
        def resp = client.post(path: "geometryapp/question")
        then:
        assert resp.status == 302
    }
    
    def "get question while authorized"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.get(path: "geometryapp/question")
        then:
        assert resp.status == 200
    }
    
    def "get json question unauth"() {
        given: "user logged in"
        when:
        client.setContentType("application/json")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/question")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get json question auth"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        client.setContentType("application/json")
        def resp = client.get(path: "geometryapp/question")
        then:
        assert resp.contentType == "application/json"
        assert resp.status == 200   
    }
    
    def "get xml question unauth"() {
        given: "user logged in"
        when:
        client.setContentType("application/xml")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/question")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get xml question auth"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        client.setContentType("application/xml")
        def resp = client.get(path: "geometryapp/question")
        then:
        assert resp.contentType == "application/xml"
        assert resp.status == 200   
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests.web

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import static org.junit.Assert.*
import groovyx.net.http.RESTClient
import groovyx.net.http.ResponseParseException

class TestMarkController extends spock.lang.Specification {

    RESTClient client = new RESTClient("http://localhost:8080/")
    
    def "get marks unauth"() {
        given: "no user logged in"
        when:
        def resp = client.post(path: "geometryapp/marks")
        then:
        assert resp.status == 302
    }
    
    def "get marks as student"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks")
        then:
        // Redirected when invalid
        assert resp.status == 302
    }
    
    def "get marks while authorized"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks")
        then:
        // Not redirected when valid
        assert resp.status == 200
    }
    
    def "get marks unauth xml"() {
        given: "no user logged in"
        when:
        client.setContentType("application/xml")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get marks as student xml"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get marks while authorized xml"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.totalAnswered != 0
    }
    
    def "get marks unauth json"() {
        given: "no user logged in"
        when:
        client.setContentType("application/json")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get marks as student json"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get marks while authorized json"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.totalAnswered != 0
    }
    
    def "get mark unauth"() {
        given: "no user logged in"
        when:
        def resp = client.post(path: "geometryapp/marks/1")
        then:
        assert resp.status == 302
    }
    
    def "get mark as student"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks/1")
        then:
        // Redirected when invalid
        assert resp.status == 302
    }
    
    def "get mark while authorized"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks/1")
        then:
        // Not redirected when valid
        assert resp.status == 200
    }
    
    def "get mark unauth xml"() {
        given: "no user logged in"
        when:
        client.setContentType("application/xml")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks/1")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get mark as student xml"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks/1")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get mark while authorized xml"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks/1")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.totalAnswered == 0
    }
    
    def "get mark unauth json"() {
        given: "no user logged in"
        when:
        client.setContentType("application/json")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks/1")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get mark as student json"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/marks/1")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
    
    def "get mark while authorized json"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/marks/1")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.totalAnswered == 0
    }
}

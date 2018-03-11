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

class TestUserController extends spock.lang.Specification {
    RESTClient client = new RESTClient("http://localhost:8080/")
    
    def "get users unauth"() {
        given: "no user logged in"
        when:
        def resp = client.post(path: "geometryapp/users")
        then:
        assert resp.status == 302
    }
    
    def "get users as student"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.post(path: "geometryapp/users")
        then:
        // Redirected when invalid
        assert resp.status == 302
    }
    
    def "get users while authorized"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
        
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.get(path: "geometryapp/users")
        then:
        // Not redirected when valid
        assert resp.status == 200
    }
    
    def "get json users unauth"() {
        given: "no user logged in"
        when:
        client.setContentType("application/json")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/users")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
        
    def "get json users as student"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
            
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/users")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
        
    def "get json users while authorized"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
            
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/json")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.get(path: "geometryapp/users")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.users != []
    }
        
    def "get xml users unauth"() {
        given: "no user logged in"
        when:
        client.setContentType("application/xml")
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/users")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
        
    def "get xml users as student"() {
        given: "user logged in"
        String username = "student1"
        String password = "123"
            
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        boolean worked = true;
        try {
            def resp = client.get(path: "geometryapp/users")
            worked = false;
        } catch (ResponseParseException e) {
            
        }
        then:
        assert worked
    }
        
    def "get xml users while authorized"() {
        given: "user logged in"
        String username = "teacher1"
        String password = "123"
            
        def jwt = client.post(path: "geometryapp/login", query: [Username:username, Password:password]).getHeaders("Authorization")
        when:
        client.setContentType("application/xml")
        client.setHeaders([Authorization: jwt.value.first()])
        def resp = client.get(path: "geometryapp/users")
        then:
        // Not redirected when valid
        assert resp.status == 200
        assert resp.data.users != []
    }
}

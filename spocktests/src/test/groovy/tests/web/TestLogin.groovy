/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import groovyx.net.http.RESTClient
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import spock.lang.Shared
import static org.junit.Assert.*
import static groovyx.net.http.ContentType.*

class TestLogin extends spock.lang.Specification {
    
    RESTClient client = new RESTClient('http://localhost:8080/')
    
    def "can login successfully"() {
        given: "a valid account"
        String username = "student1"
        String password = "123"
        when:
        def resp = client.post(path: "geometryapp/login", query: [Username:username, Password:password])
        then:
        // we redirect when successful
        assert !(resp.getHeaders("Authorization") == [])
        assert resp.status == 302
    }
    
    def "error when incorrect credentials"() {
        given: "an invalid account"
        String username = "student1"
        String password = "1234"
        when:
        def resp = client.post(path: "geometryapp/login", query: [Username:username, Password:password])
        then:
        // Stay on login page if not successful
        assert resp.status == 200
        assert resp.getHeaders("Authorization") == []
    }
}

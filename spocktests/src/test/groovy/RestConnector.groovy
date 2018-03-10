

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import groovyx.net.http.Method
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator

class RestConnector {
    private String baseUrl
    private HTTPBuilder httpBuilder
    private List<String> cookies
 
    RestConnector(String url) {
        this.baseUrl = url
        this.httpBuilder = initializeHttpBuilder()
        this.cookies = []
    }
 
    public request(Method method, ContentType contentType, String url, Map<String, Serializable> params) {
        debug("Send $method request to ${this.baseUrl}$url: $params")
        httpBuilder.request(method, contentType) { request ->
            uri.path = url
            uri.query = params
            headers.put('Cookie', cookies.join(';'))
        }
    }
 
    private HTTPBuilder initializeHttpBuilder() {
        def httpBuilder = new HTTPBuilder(baseUrl)
 
        httpBuilder.handler.success = { HttpResponseDecorator resp, reader ->
            resp.getHeaders('Set-Cookie').each {
                // [Set-Cookie: JSESSIONID=E68D4799D4D6282F0348FDB7E8B88AE9; Path=/frontoffice/; HttpOnly]
                String cookie = it.value.split(';')[0]
                debug("Adding cookie to collection: $cookie")
                cookies.add(cookie)
            }
            debug("Response: ${reader}")
            return reader
        }
        return httpBuilder
    }
 
    private debug(String message) {
        System.out.println(message) //for Gradle
    }
}

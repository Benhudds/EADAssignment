/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author Ben
 */
public enum ContentTypes {
    HTML("text/html;charset=UTF-8"),
    JSON("application/json"),
    JPEG("image/jpeg"),
    XML("application/xml");
    
    private final String text;
    
    ContentTypes(final String text) {
        this.text = text;
    }
    
    public String toString() {
        return text;
    }
}

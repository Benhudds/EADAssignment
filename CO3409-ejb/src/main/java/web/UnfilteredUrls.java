/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

public enum UnfilteredUrls {
    HOME("home"),
    LOGIN("login"),
    IMAGESERVLET("imageservlet");
    
    private final String text;
    
    UnfilteredUrls(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

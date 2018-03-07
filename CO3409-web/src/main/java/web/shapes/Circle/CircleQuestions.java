/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes.Circle;

public enum CircleQuestions {
    AREA("area"),
    CIRCUMFERENCE("circumference");
    
    private final String text;
    
    CircleQuestions(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

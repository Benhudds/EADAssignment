/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.shapes.Ellipse;

public enum EllipseQuestions {
    AREA("area"),
    PERIMITER("perimiter");
    
    private final String text;
    
    EllipseQuestions(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}



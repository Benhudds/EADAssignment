/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.shapes.Triangle;

public enum TriangleQuestions {
    AREA("area"),
    PERIMETER("perimiter");
    
    private final String text;
    
    TriangleQuestions(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

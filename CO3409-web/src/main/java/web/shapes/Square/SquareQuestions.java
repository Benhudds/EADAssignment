/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.shapes.Square;

public enum SquareQuestions {
    AREA("area"),
    PERIMETER("perimiter");
    
    private final String text;
    
    SquareQuestions(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

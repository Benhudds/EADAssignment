/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package questions;

public enum EllipseQuestions {
    AREA("area"),
    PERIMETER("perimeter");
    
    private final String text;
    
    EllipseQuestions(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}



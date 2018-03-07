/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.shapes;

public class ShapeParams {

    private Shape params = null;
    private String validateString = "";

    public Shape getShape() {
        return params;
    }

    public void setShape(Shape params) {
        this.params = params;
    }

    public ShapeParams setNewShape(Shape params) {
        this.params = params;
        return this;
    }

    public String getValidateString() {
        return validateString;
    }

    public void setValidateString(String validateString) {
        this.validateString = validateString;
    }

    public ShapeParams setNewValidateString(String validateString) {
        this.validateString = validateString;
        return this;
    }

    public boolean isValid() {
        return validateString.equals("");
    }
}

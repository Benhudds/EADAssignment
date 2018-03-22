/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapes;

public class IntParams {

    private int[] params = null;
    private String validateString = "";

    public int[] getParams() {
        return params;
    }

    public void setParams(int[] params) {
        this.params = params;
    }

    public IntParams setNewParams(int[] params) {
        this.params = params;
        return this;
    }

    public String getValidateString() {
        return validateString;
    }

    public void setValidateString(String validateString) {
        this.validateString = validateString;
    }

    public IntParams setNewValidateString(String validateString) {
        this.validateString = validateString;
        return this;
    }
    
    public boolean isValid() {
        return validateString.equals("");
    }
}

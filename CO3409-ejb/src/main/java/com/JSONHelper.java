/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletOutputStream;
import shapes.Shape;

public abstract class JSONHelper {
    public static void WriteToServletOutputStream(ServletOutputStream sos, Shape params) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(params);
        sos.println(json);
    }
    
    public static String GetJson(Serializable ser) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(ser);        
    }
}

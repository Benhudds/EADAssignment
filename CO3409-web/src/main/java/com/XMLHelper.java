/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import web.shapes.Shape;

public abstract class XMLHelper {

    public static void WriteToServletOutputStream(ServletOutputStream sos, Class c, Shape params) throws JAXBException {
        Marshaller marshaller = XMLHelper.getMarshaller(c);
        marshaller.marshal(params, sos);
    }

    public static Marshaller getMarshaller(Class c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return jaxbMarshaller;
    }
}

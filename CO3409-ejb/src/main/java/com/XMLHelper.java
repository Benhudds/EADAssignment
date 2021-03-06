/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.Serializable;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public abstract class XMLHelper {

    public static void WriteToSOSWithContext(ServletOutputStream sos, Serializable params, JAXBContext context) throws JAXBException {
        Marshaller marshaller = XMLHelper.getMarshaller(context);
        marshaller.marshal(params, sos);
    }

    public static void WriteToServletOutputStream(ServletOutputStream sos, Class c, Serializable params) throws JAXBException {
        Marshaller marshaller = XMLHelper.getMarshaller(c);
        marshaller.marshal(params, sos);
    }

    public static Marshaller getMarshaller(Class c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return jaxbMarshaller;
    }

    public static Marshaller getMarshaller(JAXBContext jaxbContext) throws JAXBException {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return jaxbMarshaller;
    }
}

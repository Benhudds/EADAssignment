/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import ejb.QuestionEntity;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
public class EntityHelper {

    @Resource(mappedName = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/NewMessage")
    private static Queue queue;

    public static void createQuestion(String question, double answer) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);

        ObjectMessage message = session.createObjectMessage();

        QuestionEntity e = new QuestionEntity();
        e.setQuestion(question);
        e.setAnswer(answer);

        message.setObject(e);
        messageProducer.send(message);
        messageProducer.close();
        connection.close();
    }
}

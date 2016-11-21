/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.movieplex7.points;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author rrahman
 */
@Named
@RequestScoped
public class SendPointsBean {

    @Inject
    JMSContext context;

    @Resource(lookup = "java:global/jms/pointsQueue")
    Queue pointsQueue;
	
    @NotNull
    @Pattern(regexp = "^\\d{2},\\d{2}", message
            = "Message format must be 2 digits, comma, 2 digits, e.g. 12,12")
    private String message;
    
    private static final Logger LOGGER = Logger.getLogger(SendPointsBean.class.getName());

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendMessage() {        
        
        LOGGER.info("Sending message: " + message);

        context.createProducer().send(pointsQueue, message);
    }

}

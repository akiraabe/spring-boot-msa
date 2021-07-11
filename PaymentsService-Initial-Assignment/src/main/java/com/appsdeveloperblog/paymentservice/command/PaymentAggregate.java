/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.paymentservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.appdeveloperblog.estore.core.commands.ProcessPaymentCommand;
import com.appdeveloperblog.estore.core.events.PaymentProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class PaymentAggregate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAggregate.class);

    @AggregateIdentifier
    private String paymentId;

    private String orderId;
    
    public PaymentAggregate() { }
    
    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand){

        LOGGER.info("PaymentAggregate's constructor is called with ProcessPaymentCommand");

    	if(processPaymentCommand.getPaymentDetails() == null) {
    		throw new IllegalArgumentException("Missing payment details");
    	}
    	
    	if(processPaymentCommand.getOrderId() == null) {
    		throw new IllegalArgumentException("Missing orderId");
    	}
    	
    	if(processPaymentCommand.getPaymentId() == null) {
    		throw new IllegalArgumentException("Missing paymentId");
    	}
	
        AggregateLifecycle.apply(new PaymentProcessedEvent(processPaymentCommand.getOrderId(), 
                processPaymentCommand.getPaymentId()));
    }

    @EventSourcingHandler
    protected void on(PaymentProcessedEvent paymentProcessedEvent){
        LOGGER.info("PaymentProcessedEvent is handled by the EventSourcingHandler");
        LOGGER.info("orderId : " + paymentProcessedEvent.getOrderId());
        LOGGER.info("paymentId : " + paymentProcessedEvent.getPaymentId());
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}

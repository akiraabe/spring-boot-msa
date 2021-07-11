/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.estore.OrdersService.query;

import com.appsdeveloperblog.estore.OrdersService.core.data.OrderEntity;
import com.appsdeveloperblog.estore.OrdersService.core.data.OrdersRepository;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderApprovedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderRejectedEvent;
import com.appsdeveloperblog.estore.OrdersService.saga.OrderSaga;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsHandler.class);

    private final OrdersRepository ordersRepository;
    
    public OrderEventsHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {

        LOGGER.info("OrderCreatedEvent is handled " + event.getOrderId());

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
 
        this.ordersRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {

        LOGGER.info("OrderApprovedEvent is handled " + orderApprovedEvent.getOrderId());

        OrderEntity orderEntity = ordersRepository.findByOrderId(orderApprovedEvent.getOrderId());

        if(orderEntity == null) {
            // TODO Do something about it
            return;
        }

        orderEntity.setOrderStatus(orderApprovedEvent.getOrderStatus());

        ordersRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {

        LOGGER.info("OrderRejectedEvent is handled " + orderRejectedEvent.getOrderId());

        OrderEntity orderEntity = ordersRepository.findByOrderId(orderRejectedEvent.getOrderId());

        orderEntity.setOrderStatus(orderRejectedEvent.getOrderStatus());
        ordersRepository.save(orderEntity);
    }
    
}

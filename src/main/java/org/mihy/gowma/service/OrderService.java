package org.mihy.gowma.service;

import org.mihy.gowma.model.Order;
import org.mihy.gowma.model.OrderCreationWrapper;
import org.mihy.gowma.model.search.OrderSearchRequest;
import org.mihy.gowma.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public Order create(OrderCreationWrapper orderCreationWrapper) {

        return orderRepository.create(orderCreationWrapper);
    }


    public Order update(Order order) {

        return orderRepository.update(order);
    }


    public void delete(Integer orderId) {

        orderRepository.delete(orderId);
    }

    public List<OrderCreationWrapper> searchOrders(OrderSearchRequest orderSearchRequest) {
        return orderRepository.findAll(orderSearchRequest);
    }


    public Order get(Integer orderId) {
        return orderRepository.getById(orderId);
    }
}

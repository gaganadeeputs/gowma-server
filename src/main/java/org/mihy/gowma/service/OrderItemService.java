package org.mihy.gowma.service;

import org.mihy.gowma.model.OrderItem;
import org.mihy.gowma.repository.querybuilder.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getOrderItemsForOrderId(Integer orderId) {
        return orderItemRepository.getOrderItemsForOrderId(orderId);
    }

    public List<OrderItem> createOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.create(orderItems);
    }

    public OrderItem update(OrderItem orderItem) {
        return orderItemRepository.update(orderItem);
    }

    public void deleteByOrderIdNId(Integer orderId, Integer OrderItemId) {
         orderItemRepository.delete(orderId);
    }
}

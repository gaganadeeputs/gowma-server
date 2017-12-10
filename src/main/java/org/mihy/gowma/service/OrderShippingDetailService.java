/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.OrderShippingDetail;
import org.mihy.gowma.repository.OrderShippingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class OrderShippingDetailService {

    @Autowired
    private OrderShippingDetailRepository orderShippingDetailRepository;

    public OrderShippingDetail create(OrderShippingDetail orderShippingDetail) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderShippingDetail.setCreatedBy(authenticatedUser.getId());
        orderShippingDetail.setCreatedDate(LocalDateTime.now());
        return orderShippingDetailRepository.create(orderShippingDetail);
    }

    public OrderShippingDetail update(OrderShippingDetail orderShippingDetail) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderShippingDetail.setCreatedBy(authenticatedUser.getId());
        orderShippingDetail.setCreatedDate(LocalDateTime.now());
        return orderShippingDetailRepository.update(orderShippingDetail);
    }

    public void delete(Integer offerId) {
        orderShippingDetailRepository.delete(offerId);
    }


    public OrderShippingDetail getOrderShippingDetailForOrderId(Integer orderId) {
        return orderShippingDetailRepository.getOrderShippingDetailForOrderId(orderId);
    }


    public OrderShippingDetail updateByOrderIdNId(Integer orderId, Integer orderShippingId, OrderShippingDetail orderShippingDetail) {
        return orderShippingDetailRepository.updateByOrderIdNId(orderId, orderShippingId, orderShippingDetail);
    }
}

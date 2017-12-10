/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.service;

import org.mihy.gowma.model.AuthenticatedUser;
import org.mihy.gowma.model.Offer;
import org.mihy.gowma.model.OfferMapping;
import org.mihy.gowma.model.OrderTransaction;
import org.mihy.gowma.model.search.OfferSearchRequest;
import org.mihy.gowma.repository.OfferRepository;
import org.mihy.gowma.repository.OrderTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderTransactionService {

    @Autowired
    private OrderTransactionRepository orderTransactionRepository;

    public OrderTransaction create(OrderTransaction orderTransaction) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderTransaction.setCreatedBy(authenticatedUser.getId());
        orderTransaction.setCreatedDate(LocalDateTime.now());
        return orderTransactionRepository.create(orderTransaction);
    }

    public OrderTransaction update( OrderTransaction orderTransaction) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderTransaction.setCreatedBy(authenticatedUser.getId());
        orderTransaction.setCreatedDate(LocalDateTime.now());
        return orderTransactionRepository.update(orderTransaction);
    }

    public void delete(Integer offerId) {
        orderTransactionRepository.delete(offerId);
    }


    public OrderTransaction getOrderTransactionForOrderId(Integer orderId) {
        return orderTransactionRepository.getOrderTransactionForOrderId(orderId);
    }
}

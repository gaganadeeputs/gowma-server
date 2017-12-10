/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model;

import java.time.LocalDateTime;

public class Order extends BaseModel {
    
    private String orderNo;
    private Integer userAddressId;
    private Integer userId;
    private LocalDateTime orderedDate;
    private OrderStatus status;


    public enum OrderStatus {
        PLACED,
        PROCESSED
    }
}

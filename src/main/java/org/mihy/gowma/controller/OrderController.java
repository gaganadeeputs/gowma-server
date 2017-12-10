package org.mihy.gowma.controller;

import io.swagger.annotations.ApiOperation;
import org.mihy.gowma.constants.EndPoints;
import org.mihy.gowma.model.Order;
import org.mihy.gowma.model.OrderCreationWrapper;
import org.mihy.gowma.model.OrderItem;
import org.mihy.gowma.model.OrderShippingDetail;
import org.mihy.gowma.model.OrderTransaction;
import org.mihy.gowma.model.search.OrderSearchRequest;
import org.mihy.gowma.service.OrderItemService;
import org.mihy.gowma.service.OrderService;
import org.mihy.gowma.service.OrderShippingDetailService;
import org.mihy.gowma.service.OrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController extends AbstractWebResponseController {


    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderItemService orderDetailsService;

    @Autowired
    private OrderTransactionService orderTransactionService;


    @Autowired
    private OrderShippingDetailService orderShippingService;


    @ApiOperation(value = "Get a order by id")
    @GetMapping(EndPoints.Order.ORDER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId) {
        return orderService.get(orderId);
    }

    @ApiOperation(value = "Create a order")
    @PostMapping(EndPoints.Order.ROOT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderCreationWrapper orderCreationWrapper) {
        return orderService.create(orderCreationWrapper);
    }

    @ApiOperation(value = "Update a order with given id")
    @PutMapping(EndPoints.Order.ORDER_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Order updateOrder(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId,
                             @RequestBody Order order) {
        order.setId(orderId);
        return orderService.update(order);
    }

    @ApiOperation(value = "Delete a order with id")
    @DeleteMapping(EndPoints.Order.ORDER_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId) {
        orderService.delete(orderId);
    }

    @ApiOperation(value = "Performs search on orders")
    @PostMapping(EndPoints.Order.ORDER_SEARCH)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrderCreationWrapper> searchOrders(@RequestBody OrderSearchRequest orderSearchRequest) {
        return orderService.searchOrders(orderSearchRequest);
    }


    //Order Details
    @ApiOperation(value = "Get a list of order items for a order id")
    @GetMapping(EndPoints.Order.ORDER_ITEM)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItem> getItemsForOrderId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId) {
        return orderDetailsService.getOrderItemsForOrderId(orderId);
    }

    @ApiOperation(value = "Add a list of order items to the existing order")
    @PostMapping(EndPoints.Order.ORDER_ITEM)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderItem> createOrderItemsForOrderId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId,
                                                      @RequestBody List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> orderItem.setOrderId(orderId));
        return orderDetailsService.createOrderItems(orderItems);
    }

    @ApiOperation(value = "Update a order item by id")
    @PutMapping(EndPoints.Order.ORDER_ITEM_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderItem updateProductImageByIdForProductId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId,
                                                        @PathVariable(EndPoints.PathVariable.ORDER_ITEM_ID) Integer orderItemId,
                                                        @RequestBody OrderItem orderItem) {
        orderItem.setId(orderItemId);
        orderItem.setOrderId(orderId);
        return orderDetailsService.update(orderItem);
    }

    @ApiOperation(value = "Delete a order item by id for a order id")
    @DeleteMapping(EndPoints.Order.ORDER_ITEM_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductImageById(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer productId,
                                       @PathVariable(EndPoints.PathVariable.ORDER_ITEM_ID) Integer productImageId) {
        orderDetailsService.deleteByOrderIdNId(productId, productImageId);
    }


    //Transaction Details

    @ApiOperation(value = "Get a order transaction for a order with id")
    @GetMapping(EndPoints.Order.ORDER_TRANSACTION)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderTransaction getOrderTransactionForOrderId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId) {
        return orderTransactionService.getOrderTransactionForOrderId(orderId);
    }


    // Shipment detail

    @ApiOperation(value = "Update a shipment detail for a order with id")
    @GetMapping(EndPoints.Order.ORDER_SHIPPING_DETAILS)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderShippingDetail getOrderShippingDetailForOrderId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId) {
        return orderShippingService.getOrderShippingDetailForOrderId(orderId);
    }

    @ApiOperation(value = "Update a shipment detail for a order with id")
    @PutMapping(EndPoints.Order.ORDER_SHIPPING_DETAILS_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderShippingDetail getOrderShippingDetailForOrderId(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId,
                                                                @PathVariable(EndPoints.PathVariable.ORDER_SHIIPING_ID) Integer orderShippingId,
                                                                @RequestBody OrderShippingDetail orderShippingDetail) {
        return orderShippingService.updateByOrderIdNId(orderId, orderShippingId,orderShippingDetail);
    }

    //check with murali do we really need the following two functionality
  /*  @ApiOperation(value = "Update a order transaction with id")
    @PutMapping(EndPoints.Order.ORDER_TRANSACTION_WITH_ID)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderTransaction updateOrderTransaction(@PathVariable(EndPoints.PathVariable.ORDER_ID) Integer orderId,
                                                               @PathVariable(EndPoints.PathVariable.ORDER_TXN_ID) Integer orderTxnId,
                                                               @RequestBody OrderTransaction orderTransaction) {
        orderTransaction.setId(orderTxnId);
        orderTransaction.setOrderId(orderId);
        return orderTransactionService.update(orderTransaction);
    }

    @ApiOperation(value = "Delete a product inventory for a product id",s)
    @DeleteMapping(EndPoints.Product.PRODUCT_INVENTORY_WITH_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductInventoryByIdNProductId(@PathVariable(EndPoints.PathVariable.PRODUCT_ID) Integer productId,
                                                     @PathVariable(EndPoints.PathVariable.PRODUCT_INVENTORY_ID) Integer productInventoryId) {
        orderTransactionService.deleteByProductIdNId(productId, productInventoryId);
    }*/

}

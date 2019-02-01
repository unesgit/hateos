/**
 * 
 */
package com.training.hateos.services;

import java.util.List;

import com.training.hateos.exception.OrderNotFoundException;
import com.training.hateos.model.Order;

/**
 * @author y.nadir
 *
 */
public interface OrderService {
    List<Order> getAllOrdersForCustomer(Long customerId);
    
    Order getOrderById(Long customerId, Long orderId) throws OrderNotFoundException;
}

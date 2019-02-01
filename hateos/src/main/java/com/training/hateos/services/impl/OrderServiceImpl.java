/**
 * 
 */
package com.training.hateos.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.training.hateos.exception.OrderNotFoundException;
import com.training.hateos.model.Customer;
import com.training.hateos.model.Order;
import com.training.hateos.services.OrderService;

/**
 * @author y.nadir
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    private List<Order> orderList = new ArrayList<>();
    
    @Override
    public List<Order> getAllOrdersForCustomer(Long customerId) {
        orderList.clear();
        
        Order order1 = new Order(1l, 120, 5, new Customer(1l));
        orderList.add(order1);
        Order order2 = new Order(2l, 200, 10, new Customer(1l));
        orderList.add(order2);
        
        return orderList;
    }
    
    @Override
    public Order getOrderById(Long customerId, Long orderId) throws OrderNotFoundException {
        
        List<Order> orderNarrowedList = orderList.stream()
                .filter((Order o) -> o.getCustomer().getCustomerId().equals(customerId) && o.getOrderId().equals(orderId))
                .collect(Collectors.toList());
        if (orderNarrowedList == null || orderNarrowedList.isEmpty()) {
            throw new OrderNotFoundException("order " + orderId + "not found for customer " + customerId);
        }
        return orderNarrowedList.get(0);
    }
    
}

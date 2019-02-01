/**
 * 
 */
package com.training.hateos.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author y.nadir
 *
 */
public class Order extends ResourceSupport {
    private Long orderId;
    private double price;
    private int quantity;
    private Customer customer;
    
    public Order(Long orderId, double price, int quantity, Customer customer) {
        super();
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
        this.customer = customer;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}

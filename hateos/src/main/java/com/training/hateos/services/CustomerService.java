/**
 * 
 */
package com.training.hateos.services;

import java.util.List;

import com.training.hateos.model.Customer;

/**
 * @author y.nadir
 *
 */
public interface CustomerService {
    
    Customer findCustomerById(Long customerId);
    
    List<Customer> findAll();
}

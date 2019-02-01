/**
 * 
 */
package com.training.hateos.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.training.hateos.model.Customer;
import com.training.hateos.services.CustomerService;

/**
 * @author y.nadir
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    
    @Override
    public Customer findCustomerById(Long customerId) {
        return new Customer(1l, "younes nadir", "unesal");
    }
    
    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1l, "younes nadir", "unesal"));
        customerList.add(new Customer(2l, "customer 2", "customer2 Société"));
        
        return customerList;
    }
    
}

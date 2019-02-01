/**
 * 
 */
package com.training.hateos.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author y.nadir
 *
 */
public class Customer extends ResourceSupport {
    private Long customerId;
    private String customerName;
    private String companyName;
    
    public Customer(Long customerId) {
        this.customerId = customerId;
    }
    
    public Customer(Long customerId, String customerName, String companyName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}

package com.training.hateos.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.training.hateos.controler.CustomerController;
import com.training.hateos.model.Customer;

@Component
public class CustomerResourceAssembler implements ResourceAssembler<Customer, Resource<Customer>> {
    
    @Override
    public Resource<Customer> toResource(Customer customer) {
        return new Resource<Customer>(customer, linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel(),
                linkTo(methodOn(CustomerController.class).findOrdersForCustomer(customer.getCustomerId())).withRel("allOrders"));
    }
    
}

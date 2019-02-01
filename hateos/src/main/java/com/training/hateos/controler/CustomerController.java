/**
 * 
 */
package com.training.hateos.controler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.hateos.exception.OrderNotFoundException;
import com.training.hateos.model.Customer;
import com.training.hateos.model.Order;
import com.training.hateos.services.CustomerService;
import com.training.hateos.services.OrderService;

/**
 * @author y.nadir
 *
 */
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    OrderService orderService;
    
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    Customer findCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        
        // the linkTo() method inspects the controller class and obtains its
        // root mapping
        // the slash() method adds the customerId value as the path variable of
        // the link
        // finally, the withSelfMethod() qualifies the relation as a self-link
        customer.add(linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel());
        
        return customer;
    }
    
    @RequestMapping(value = "/{customerId}/orders", method = RequestMethod.GET, produces = { "application/hal+json" })
    public Resources<Order> findOrdersForCustomer(@PathVariable final Long customerId) throws OrderNotFoundException {
        List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final Order order : orders) {
            Link link = linkTo(methodOn(CustomerController.class).getOrderById(customerId, order.getOrderId())).withRel("OrderDetails");
            order.add(link);
        }
        
        Link selfLink = linkTo(CustomerController.class).slash(customerId).withSelfRel();
        Resources<Order> result = new Resources<Order>(orders, selfLink);
        return result;
    }
    
    @RequestMapping(value = "/{customerId}/orders/{orderId}", method = RequestMethod.GET, produces = { "application/hal+json" })
    public Order getOrderById(@PathVariable final Long customerId, @PathVariable final Long orderId) throws OrderNotFoundException {
        return orderService.getOrderById(customerId, orderId);
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = { "application/hal+json" })
    
    public Resources<Customer> findAllCustomers() throws OrderNotFoundException {
        List<Customer> allCustomers = customerService.findAll();
        
        for (Customer customer : allCustomers) {
            Link selfLink = linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel();
            customer.add(selfLink);
            if (orderService.getAllOrdersForCustomer(customer.getCustomerId()).size() > 0) {
                Link ordersLink = linkTo(methodOn(CustomerController.class).findOrdersForCustomer(customer.getCustomerId()))
                        .withRel("allOrders");
                customer.add(ordersLink);
            }
        }
        
        Link link = linkTo(CustomerController.class).withSelfRel();
        Resources<Customer> result = new Resources<Customer>(allCustomers, link);
        return result;
    }
    
}

/**
 * 
 */
package com.training.hateos.controler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.hateos.assembler.CustomerResourceAssembler;
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
    
    @Autowired
    CustomerResourceAssembler customerResourceAssembler;
    
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
    public Resources<Order> findOrdersForCustomer(@PathVariable final Long customerId) {
        List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final Order order : orders) {
            Link link = linkTo(methodOn(CustomerController.class).getOrderById(customerId, order.getOrderId())).withRel("OrderDetails");
            order.add(link);
        }
        
        Link selfLink = linkTo(CustomerController.class).slash(customerId).slash("orders").withSelfRel();
        Resources<Order> result = new Resources<Order>(orders, selfLink);
        return result;
    }
    
    @RequestMapping(value = "/{customerId}/orders/{orderId}", method = RequestMethod.GET, produces = { "application/hal+json" })
    public Order getOrderById(@PathVariable final Long customerId, @PathVariable final Long orderId) {
        try {
            return orderService.getOrderById(customerId, orderId);
        } catch (OrderNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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
        
        Link link = linkTo(CustomerController.class).slash("all").withSelfRel();
        Resources<Customer> result = new Resources<Customer>(allCustomers, link);
        return result;
    }
    
    @RequestMapping(value = "/allStreamApi", method = RequestMethod.GET, produces = { "application/hal+json" })
    
    public Resources<Resource<Customer>> findAllCustomersStream() throws OrderNotFoundException {
        List<Resource<Customer>> allCustomers = customerService.findAll().stream()
                .map(customer -> new Resource<Customer>(customer,
                        linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel(),
                        linkTo(methodOn(CustomerController.class).findOrdersForCustomer(customer.getCustomerId())).withRel("allOrders")))
                .collect(Collectors.toList());
        
        return new Resources<>(allCustomers, linkTo(CustomerController.class).slash("all").withSelfRel());
    }
    
    @RequestMapping(value = "/allAssembled", method = RequestMethod.GET, produces = { "application/hal+json" })
    
    public Resources<Resource<Customer>> findAllCustomersAssembled() throws OrderNotFoundException {
        List<Resource<Customer>> allCustomers = customerService.findAll().stream()
                .map(customer -> customerResourceAssembler.toResource(customer)).collect(Collectors.toList());
        
        return new Resources<>(allCustomers, linkTo(CustomerController.class).slash("all").withSelfRel());
    }
    
}

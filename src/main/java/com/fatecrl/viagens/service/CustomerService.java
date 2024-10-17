package com.fatecrl.viagens.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Customer;

@Service
public class CustomerService {

    private static List<Customer> customers = new ArrayList<Customer>();

    public CustomerService(){
        //this is a "fake" customer
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setLastname("Stern");
        customer.setAddress("R. Min. Joao Mendes 177");
        customer.setCity("Santos");
        customer.setState(11);
        customer.setCountry("Brasil");
        customer.setBirthDate( LocalDate.now().minusYears(25) );
        customer.setLimitAmount(3000);
        customers.add(customer);
    }

    public List<Customer> findAll(){
        return customers;
    }
}
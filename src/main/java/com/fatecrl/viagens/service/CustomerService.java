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

    public Customer find( Long id ){
        return customers.stream()
            .filter( c -> c.getId() == id )
            .findFirst().orElse(null);
    }

    public Customer find( Customer customer ){
        return customers.stream()
            .filter( c -> c.equals(customer) )
            .findFirst().orElse(null);
    }

    public void create( Customer customer ){
        Long newId = (long) (customers.size()+1);
        customer.setId( newId );
        customers.add( customer );
    }

    public Boolean update( Customer customer ){
        Customer _cust = find( customer );
        if( _cust != null ){
            if( !customer.getName().isBlank() )
                _cust.setName( customer.getName() );
            if( !customer.getLastname().isBlank() )
                _cust.setLastname( customer.getLastname() );
            if( !customer.getAddress().isBlank() )
                _cust.setAddress( customer.getAddress() );
            if( !customer.getCity().isBlank() )
                _cust.setCity( customer.getCity() );
            if( customer.getState() != null )
                _cust.setState( customer.getState() );
            if( !customer.getCountry().isBlank() )
                _cust.setCountry( customer.getCountry() );
            if( customer.getBirthDate() != null )
                _cust.setBirthDate( customer.getBirthDate() );
            if( customer.getLimitAmount() != null )
                _cust.setLimitAmount( customer.getLimitAmount() );
            return true;
        }
        return false;
    }

    public Boolean delete( Long id ){
        Customer customer = find(id);
        if( customer != null ){
            customers.remove( customer );
            return true;
        }
        return false;
    }
}

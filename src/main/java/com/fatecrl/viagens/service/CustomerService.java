package com.fatecrl.viagens.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Status;

@Service
public class CustomerService implements IService<Customer> {

    private static List<Customer> customers = new ArrayList<Customer>();

    public CustomerService(){
        //this is a "fake" customer
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setLastname("Stern");
        customer.setAddress("R. Min. Joao Mendes 177");
        customer.setCity("Santos");
        customer.setState("SP");
        customer.setCountry("Brasil");
        customer.setBirthDate( LocalDate.now().minusYears(25) );
        customer.setLimitAmount(3000);
        customer.setStatus( Status.ACTIVE );
        customers.add(customer);
    }

    @Override
    public List<Customer> findAll(){
        return customers;
    }

    @Override
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

    public List<Customer> findByParams(
        String name ,
        LocalDate birthDate
    ){
        List<Customer> custs;
        if( name != null && !name.isEmpty() ){
            custs = customers.stream()
                .filter( c -> c.getName()
                    .toLowerCase()
                    .indexOf( name.toLowerCase() ) > -1
                )
                .toList();
            if( birthDate != null ){
                custs = custs.stream()
                    .filter( c -> c.getBirthDate()
                        .equals( birthDate )
                    )
                    .toList();
            }
        }
        else{
            custs = customers.stream()
                .filter( c -> c.getBirthDate()
                    .equals( birthDate )
                )
                .toList();
        }
        return custs;
    }

    @Override
    public void create( Customer customer ){
        Long newId = (long) (customers.size()+1);
        customer.setId( newId );
        customers.add( customer );
    }

    @Override
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

    public Boolean updateStatus( Long id, Customer customer ){
        Customer _cust = find( id );
        if( _cust != null ){
            if( customer.getStatus() != null )
                _cust.setStatus( customer.getStatus() );
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete( Long id ){
        Customer customer = find(id);
        if( customer != null ){
            customers.remove( customer );
            return true;
        }
        return false;
    }
}

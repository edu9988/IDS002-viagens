package com.fatecrl.viagens.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.repository.CustomerRepository;

@Service
public class CustomerService implements IService<Customer> {

    @Autowired
    private CustomerRepository repo;

    public CustomerService(){
    }

    @Override
    public List<Customer> findAll(){
        return repo.findAll();
    }

    @Override
    public Optional<Customer> find( Long id ){
        return repo.findById(id);
    }

    /*
    public Customer find( Customer customer ){
        return customers.stream()
            .filter( c -> c.equals(customer) )
            .findFirst().orElse(null);
    }
    */

    public List<Customer> findByParams(
        String name ,
        LocalDate birthDate
    ){
        List<Customer> custs = repo.findAll();
        if( name != null && !name.isEmpty() ){
            custs = custs.stream()
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
            custs = custs.stream()
                .filter( c -> c.getBirthDate()
                    .equals( birthDate )
                )
                .toList();
        }
        return custs;
    }

    @Override
    public void create( Customer customer ){
        repo.save(customer);
    }

    @Override
    public Boolean update( Customer customer ){
        if( repo.existsById( customer.getId() ) ){
            repo.save(customer);
            return true;
        }
        return false;
    }

    public Boolean updateStatus( Long id, Customer customer ){
        Optional<Customer> _cust = repo.findById( id );
        if( _cust.isPresent() ){
            if( customer.getStatus() != null )
                _cust.get().setStatus( customer.getStatus() );
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete( Long id ){
        if( repo.existsById(id) ){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}

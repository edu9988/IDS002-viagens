package com.fatecrl.viagens.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.repository.CustomerRepository;
import com.fatecrl.viagens.repository.TravelRepository;

@Service
public class CustomerService implements IService<Customer> {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private TravelRepository travelRepo;

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

    public List<Customer> findByParams(
        String name ,
        LocalDate birthDate
    ){
        List<Customer> customers;
        if( name ==  null || name.isEmpty() )
            customers = repo
                .findByBirthDate(birthDate);
        else if( birthDate == null )
            customers = repo
                .findByNameContaining(name);
        else
            customers = repo
                .findByNameContainingAndBirthDate(name, birthDate);
        return customers;
    }

    @Override
    public void create( Customer customer ){
        repo.save(customer);
    }

    public Boolean customerExists( Long id ){
        return repo.existsById(id);
    }
    
    @Override
    public void update( Long id, Customer customer ){
        customer.setId(id); //might need this
        repo.save(customer);
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

    public Boolean referencedBySomeTravel( Long id ){
        Customer target = repo.findById(id).orElse(null);
        return travelRepo.existsByCustomer( target );
    }

    @Override
    public void delete( Long id ){
        repo.deleteById(id);
    }
}

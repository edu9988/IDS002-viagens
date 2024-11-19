package com.fatecrl.viagens.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    //@Override
    public Page<Customer> findAll( Pageable pageable ){
        return repo.findAll( pageable );
    }

    @Override
    public Optional<Customer> find( Long id ){
        return repo.findById(id);
    }

    public Page<Customer> findByParams(
        String name ,
        LocalDate birthDate,
        Pageable pageable
    ){
        Page<Customer> customers;
        if( name ==  null || name.isEmpty() )
            customers = repo
                .findByBirthDate(birthDate,pageable);
        else if( birthDate == null )
            customers = repo
                .findByNameContaining(name,pageable);
        else
            customers = repo
                .findByNameContainingAndBirthDate(name, birthDate,pageable);
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

package com.fatecrl.viagens.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.dto.TravelDatesDTO;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.repository.CustomerRepository;
import com.fatecrl.viagens.repository.LocationRepository;
import com.fatecrl.viagens.repository.TravelRepository;

@Service
public class TravelService implements IService<Travel>{

    @Autowired
    private TravelRepository repo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private LocationRepository locationRepo;

    public TravelService(){
    }

    @Override
    public Page<Travel> findAll( Pageable pageable ){
        return repo.findAll( pageable );
    }

    @Override
    public Optional<Travel> find( Long id ){
        return repo.findById(id);
    }

    public Optional<Customer> findCustomer( Long id ){
        return customerRepo.findById( id );
    }

    public Optional<Location> findLocation( Long id ){
        return locationRepo.findById( id );
    }

    public Boolean hasNoFunds( Long customerId , BigDecimal cost ){
        Customer c = customerRepo.findById(customerId).orElse(null);
        int res = c.getLimitAmount().compareTo(cost);
        
        if( res >= 0 )
            return false;
        return true;
    }

    public Boolean hasNoFunds( Long customerId , BigDecimal cost, Long travelId ){
        Customer c = customerRepo.findById(customerId).orElse(null);
        Travel t = find(travelId).orElse(null); //already checked for null in Controller
        int res = c.getLimitAmount().compareTo(cost.subtract(t.getAmount()));        
        if(  res >= 0 )
            return false;
        return true;
    }

    public Boolean travelExists( Long travelId ){
        return repo.existsById(travelId);
    }

    public Boolean hasConflictingDates(
        Customer customer ,
        LocalDateTime start,
        LocalDateTime end
    ){
        List<Travel> ts = repo.findByCustomer(customer);

        ts = ts.stream().filter(t ->
            (start.isAfter( t.getStartDateTime() )
            && start.isBefore( t.getEndDateTime() ))
            ||
            (end.isAfter( t.getStartDateTime() )
            && end.isBefore( t.getEndDateTime() ))
            ||
            start.isEqual(t.getStartDateTime())
            ||
            end.isEqual(t.getEndDateTime())
            ||
            (t.getStartDateTime().isAfter(start)
            && t.getStartDateTime().isBefore(end))
            ||
            (t.getEndDateTime().isAfter(start)
            && t.getEndDateTime().isBefore(end))
        ).toList();

        return !ts.isEmpty();
    }

    public Boolean hasConflictingDates(
        Customer customer ,
        LocalDateTime start,
        LocalDateTime end,
        Long travelId
    ){
        List<Travel> ts = repo.findByCustomer(customer);

        ts = ts.stream().filter(t ->
            t.getId().compareTo( travelId ) != 0
        ).toList();

        ts = ts.stream().filter(t ->
            (start.isAfter( t.getStartDateTime() )
            && start.isBefore( t.getEndDateTime() ))
            ||
            (end.isAfter( t.getStartDateTime() )
            && end.isBefore( t.getEndDateTime() ))
            ||
            start.isEqual(t.getStartDateTime())
            ||
            end.isEqual(t.getEndDateTime())
            ||
            (t.getStartDateTime().isAfter(start)
            && t.getStartDateTime().isBefore(end))
            ||
            (t.getEndDateTime().isAfter(start)
            && t.getEndDateTime().isBefore(end))
        ).toList();

        return !ts.isEmpty();
    }

    @Override
    public void create( Travel travel ){
        Customer c = findCustomer(travel.getCustomer().getId()).orElse(null);
        //this is safe because the only place where this methods gets called
        //is in TravelController PostMapping, where existence was already
        //checked
        c.setLimitAmount( c.getLimitAmount().subtract( travel.getAmount()) );

        customerRepo.save(c);
        repo.save(travel);
    }

    @Override
    public void update( Long id , Travel travel ){
        Customer c = findCustomer(travel.getCustomer().getId()).orElse(null);
        Travel oldTravel = repo.findById(id).orElse(null);
        //this is safe because the only place where this methods gets called
        //is in TravelController PutMapping, where existence was already
        //checked
        c.setLimitAmount( c.getLimitAmount()
            .subtract( travel.getAmount()
                .subtract(oldTravel.getAmount())
            )
        );

        customerRepo.save(c);

        travel.setId(id);
        repo.save(travel);
    }

    public void updateDates( Travel t, TravelDatesDTO dates ){
        t.setStartDateTime( dates.getStartDateTime() );
        t.setEndDateTime( dates.getEndDateTime() );
        repo.save(t);
    }

    @Override
    public void delete( Long id ){
        repo.deleteById(id);
    }
}

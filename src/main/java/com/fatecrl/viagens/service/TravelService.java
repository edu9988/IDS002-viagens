package com.fatecrl.viagens.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.model.TripType;

@Service
public class TravelService implements IService<Travel>{

    private static List<Travel> travels = new ArrayList<Travel>();

    public TravelService(){
        //this is a "fake" travel
        Travel travel = new Travel();
        travel.setId(1L);
        travel.setOrderNumber("12345");
        travel.setAmount(3);
        travel.setSource(null);
        travel.setDestination(null);
        travel.setStartDateTime( LocalDateTime.now().plusWeeks(1L) );
        travel.setEndDateTime( LocalDateTime.now().plusWeeks(2L) );
        travel.setType( TripType.RETURN );
        travel.setCustomer(null);

        travels.add(travel);
    }

    @Override
    public List<Travel> findAll(){
        return travels;
    }

    @Override
    public Travel find( Long id ){
        return travels.stream()
            .filter( t -> t.getId() == id )
            .findFirst().orElse(null);
    }

    public Travel find( Travel travel ){
        return travels.stream()
            .filter( t -> t.equals(travel) )
            .findFirst().orElse(null);
    }

    @Override
    public void create( Travel travel ){
        //verify Customer limit before inserting...
        //must know travel cost??
        
        Long newId = (long) (travels.size()+1);
        travel.setId( newId );
        travels.add( travel );
    }

    @Override
    public Boolean update( Travel travel ){
        Travel _trav = find( travel );
        if( _trav != null ){
            if( !travel.getOrderNumber().isBlank() )
                _trav.setOrderNumber( travel.getOrderNumber() );
            if( travel.getAmount() != null )
                _trav.setAmount( travel.getAmount() );
            if( travel.getSource() != null )
                _trav.setSource( travel.getSource() );
            if( travel.getDestination() != null )
                _trav.setDestination( travel.getDestination() );
            if( travel.getStartDateTime() != null )
                _trav.setStartDateTime( travel.getStartDateTime() );
            if( travel.getEndDateTime() != null )
                _trav.setEndDateTime( travel.getEndDateTime() );
            if( travel.getType() != null )
                _trav.setType( travel.getType() );
            if( travel.getCustomer() != null )
                _trav.setCustomer( travel.getCustomer() );
            return true;
        }
        return false;
    }

    public Boolean updateDates( Travel travel ){
        Travel _trav = find( travel );
        if( _trav != null ){
            if( travel.getStartDateTime() != null )
                _trav.setStartDateTime( travel.getStartDateTime() );
            if( travel.getEndDateTime() != null )
                _trav.setEndDateTime( travel.getEndDateTime() );
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete( Long id ){
        Travel travel = find(id);
        if( travel != null ){
            travels.remove( travel );
            return true;
        }
        return false;
    }
}

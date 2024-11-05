package com.fatecrl.viagens.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecrl.viagens.model.Travel;
import com.fatecrl.viagens.repository.TravelRepository;

@Service
public class TravelService implements IService<Travel>{

    @Autowired
    private TravelRepository repo;

    public TravelService(){
    }

    @Override
    public List<Travel> findAll(){
        return repo.findAll();
    }

    @Override
    public Optional<Travel> find( Long id ){
        return repo.findById(id);
    }

    /*
    public Travel find( Travel travel ){
        return travels.stream()
            .filter( t -> t.equals(travel) )
            .findFirst().orElse(null);
    }
    */

    @Override
    public void create( Travel travel ){
        //verify Customer limit before inserting...
        //must know travel cost??
        repo.save(travel);
    }

    @Override
    public Boolean update( Long id , Travel travel ){
        if( repo.existsById( id ) ){
            travel.setId(id);
            repo.save(travel);
            return true;
        }
        return false;
    }

    public Boolean updateDates( Long id, Travel travel ){
        Optional<Travel> _trav = repo.findById( travel.getId() );
        if( _trav.isPresent() ){
            if( travel.getStartDateTime() != null )
                _trav.get().setStartDateTime( travel.getStartDateTime() );
            if( travel.getEndDateTime() != null )
                _trav.get().setEndDateTime( travel.getEndDateTime() );
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

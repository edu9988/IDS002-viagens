package com.fatecrl.viagens.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fatecrl.viagens.dto.TravelDTO;
import com.fatecrl.viagens.dto.TravelDatesDTO;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.model.Location;
import com.fatecrl.viagens.model.Travel;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class TravelMapper {

    public Travel toEntity(
        TravelDTO dto,
        Customer c,
        Location source,
        Location destination
    ){
        Travel t = new Travel();
        t.setAmount(dto.getAmount());
        t.setCustomer(c);
        t.setDestination(destination);
        t.setEndDateTime(dto.getEndDateTime());
        t.setOrderNumber(dto.getOrderNumber());
        t.setSource(source);
        t.setStartDateTime(dto.getStartDateTime());
        t.setType(dto.getType());
        return t;
    }

    public Travel toEntity( TravelDatesDTO datesDTO ){
        Travel t = new Travel();
        t.setStartDateTime( datesDTO.getStartDateTime() );
        t.setEndDateTime( datesDTO.getEndDateTime() );
        return t;
    }

    public TravelDTO toDTO( Travel t ){
        return new TravelDTO(
            t.getId(),
            t.getOrderNumber(),
            t.getAmount(),
            t.getSource().getId(),
            t.getDestination().getId(),
            t.getStartDateTime(),
            t.getEndDateTime(),
            t.getType(),
            t.getCustomer().getId()
        );
    }

    public List<TravelDTO> toDTO( List<Travel> ts ){
        return ts.stream().map(
            t -> toDTO(t)
        ).toList();
    }
}

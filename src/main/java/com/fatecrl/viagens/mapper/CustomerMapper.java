package com.fatecrl.viagens.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fatecrl.viagens.dto.CustomerDTO;
import com.fatecrl.viagens.dto.CustomerStatusDTO;
import com.fatecrl.viagens.model.Customer;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CustomerMapper {

    public Customer toEntity( CustomerDTO dto ){
        Customer c = new Customer();
        // don't copy id
        c.setAddress(dto.getAddress());
        c.setBirthDate(dto.getBirthDate());
        c.setCity(dto.getCity());
        c.setCountry(dto.getCountry());
        c.setLastname(dto.getLastname());
        c.setLimitAmount(dto.getLimitAmount());
        c.setName(dto.getName());
        c.setState(dto.getState());
        c.setStatus(dto.getStatus());
        return c;
    }

    public Customer toEntity( CustomerStatusDTO statusDTO ){
        Customer c = new Customer();
        // don't copy id
        c.setStatus(statusDTO.getStatus());
        return c;
    }

    public CustomerDTO toDTO( Customer c ){
        return new CustomerDTO(
            c.getId(),
            c.getName(),
            c.getLastname(),
            c.getAddress(),
            c.getCity(),
            c.getState(),
            c.getCountry(),
            c.getBirthDate(),
            c.getLimitAmount(),
            c.getStatus()
        );
    }

    /* unnecessary?
    public List<Customer> toEntity( List<CustomerDTO> dtos ){
        return dtos.stream().map(
            dto -> toEntity(dto)
        ).toList();
    }
    */

    public List<CustomerDTO> toDTO( List<Customer> cs ){
        return cs.stream().map(
            c -> toDTO(c)
        ).toList();
    }
}

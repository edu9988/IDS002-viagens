package com.fatecrl.viagens.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.viagens.dto.CustomerDTO;
import com.fatecrl.viagens.dto.CustomerStatusDTO;
import com.fatecrl.viagens.mapper.CustomerMapper;
import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper mapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) LocalDate birthDate
    ){
        System.out.println( "name: " + name );
        System.out.println( "birthDate: " + birthDate );

        if( (name != null && !name.isEmpty()) ||
            (birthDate != null)
        ){
            List<Customer> customers = customerService
                .findByParams(name,birthDate);
            if( customers != null && customers.size() > 0 )
                return ResponseEntity.ok( mapper.toDTO(customers) );
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
            mapper.toDTO( customerService.findAll() )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> get( @PathVariable("id") Long id ){
        Customer customer = customerService.find(id).orElse(null);

        if( customer != null )
            return ResponseEntity.ok( mapper.toDTO(customer) );
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create( @Valid @RequestBody CustomerDTO dto ){
        Customer entity = mapper.toEntity(dto);
        customerService.create( entity );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(  entity.getId() )
            .toUri();
        dto.setId( entity.getId() );
        return ResponseEntity.created( uri ).body( dto );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> update( @RequestBody CustomerDTO dto ){
        if( customerService.update( mapper.toEntity(dto) ) )
            return ResponseEntity.ok(dto);
        
        return ResponseEntity.notFound().build();
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }   

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patch(
        @PathVariable("id") Long id,
        @RequestBody CustomerStatusDTO statusDTO
    ){
        //System.out.println("id: "+customer.getId());
        //System.out.println("name: "+customer.getName());
        //System.out.println("status: "+customer.getStatus());
        if( customerService.updateStatus(id,mapper.toEntity(statusDTO)) )
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> delete( @PathVariable("id") Long id ) {
        if( customerService.delete(id) )
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.notFound().build();
    }
}
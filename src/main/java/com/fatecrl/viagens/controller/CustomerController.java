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

import com.fatecrl.viagens.model.Customer;
import com.fatecrl.viagens.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAll(
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
                return ResponseEntity.ok(customers);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get( @PathVariable("id") Long id ){
        Customer customer = customerService.find(id);

        if( customer != null )
            return ResponseEntity.ok(customer);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> create( @RequestBody Customer customer ){
        customerService.create( customer );
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand( customer.getId() )
            .toUri();
        return ResponseEntity.created( uri ).body( customer );
        //return ResponseEntity.badRequest()            (400)  (to do)
        //return ResponseEntity.unprocessable()         (422)  (to do)
        //return ResponseEntity.internalServerError()   (500)  (to do)
    }

    @PutMapping
    public ResponseEntity<Customer> update( @RequestBody Customer customer ){
        if( customerService.update(customer) )
            return ResponseEntity.ok(customer);
        
        return ResponseEntity.notFound().build();
        //return ResponseEntity.badRequest()      (400)  (to do)
        //return ResponseEntity.unprocessable()   (422)  (to do)
    }

    @PatchMapping
    public ResponseEntity<Customer> patch( @RequestBody Customer customer ){
        //System.out.println("id: "+customer.getId());
        //System.out.println("name: "+customer.getName());
        //System.out.println("status: "+customer.getStatus());
        if( customerService.updateStatus(customer) )
            return ResponseEntity.ok(
                customerService.find(customer.getId())
            );
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete( @PathVariable("id") Long id ) {
        if( customerService.delete(id) )
            return ResponseEntity.noContent().build();
        
        return ResponseEntity.notFound().build();
    }
}
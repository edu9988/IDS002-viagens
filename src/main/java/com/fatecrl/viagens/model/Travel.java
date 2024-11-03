package com.fatecrl.viagens.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Travel implements Serializable{

    private Long id;
    private String orderNumber;
    private BigDecimal amount;
    private Location source;
    private Location destination;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private TripType type;
    private Customer customer;
    
    public void setAmount(int amount) {
        this.amount = new BigDecimal( amount );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override  // copied from Location
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Travel other = (Travel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        
        return true;
    }
}
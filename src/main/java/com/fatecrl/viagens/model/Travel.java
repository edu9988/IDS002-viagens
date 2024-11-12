package com.fatecrl.viagens.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "tb_Travel" )
public class Travel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_Travel")
    private Long id;
    @Column(name = "ds_OrderNumber")
    private String orderNumber;
    @Column(name = "vl_Amount")
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "cd_Source", referencedColumnName = "cd_Location")
    private Location source;
    @ManyToOne
    @JoinColumn(name = "cd_Destination", referencedColumnName = "cd_Location")
    private Location destination;
    @Column(name = "dt_StartDateTime")
    private LocalDateTime startDateTime;
    @Column(name = "dt_EndDateTime")
    private LocalDateTime endDateTime;
    @Column(name = "ds_Type")
    private TripType type;
    @ManyToOne
    @JoinColumn(name = "cd_Customer", referencedColumnName = "cd_Customer")
    private Customer customer;
    
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
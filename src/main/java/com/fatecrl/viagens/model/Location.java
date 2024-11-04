package com.fatecrl.viagens.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "tb_Location" )
public class Location implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_Location")
    private Long id;
    @Column(name = "nm_Location")
    private String name;
    @Column(name = "nm_Nickname")
    private String nickname;
    @Column(name = "ds_Address")
    private String address;
    @Column(name = "nm_City")
    private String city;
    @Column(name = "sg_State")
    private String state;
    @Column(name = "nm_Country")
    private String country;
    
    @Override  // generated automatically
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override  // generated automatically, then edited
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        
        return true;
    }
}

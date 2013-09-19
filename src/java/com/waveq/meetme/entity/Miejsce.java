/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.meetme.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Szym
 */
@Entity
@Table(name = "miejsce")
@NamedQueries({
    @NamedQuery(name = "Miejsce.findAll", query = "SELECT m FROM Miejsce m"),
    @NamedQuery(name = "Miejsce.findById", query = "SELECT m FROM Miejsce m WHERE m.id = :id"),
    @NamedQuery(name = "Miejsce.findByNazwa", query = "SELECT m FROM Miejsce m WHERE m.nazwa = :nazwa"),
    @NamedQuery(name = "Miejsce.findByAdres", query = "SELECT m FROM Miejsce m WHERE m.adres = :adres")})
public class Miejsce implements Serializable {
    @OneToMany(mappedBy = "miejsce", fetch = FetchType.EAGER)
    private Set<Event> eventSet;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nazwa", nullable = false, length = 75)
    private String nazwa;
    @Column(name = "adres", length = 50)
    private String adres;

    public Miejsce() {
    }

    public Miejsce(Integer id) {
        this.id = id;
    }

    public Miejsce(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miejsce)) {
            return false;
        }
        Miejsce other = (Miejsce) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.waveq.meetme.Miejsce[ id=" + id + " ]";
    }

    public Set<Event> getEventSet() {
        return eventSet;
    }

    public void setEventSet(Set<Event> eventSet) {
        this.eventSet = eventSet;
    }
    
}

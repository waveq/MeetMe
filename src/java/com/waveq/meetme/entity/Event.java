/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waveq.meetme.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Szym
 */
@Entity
@Table(name = "event")
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByNazwa", query = "SELECT e FROM Event e WHERE e.nazwa = :nazwa"),
    @NamedQuery(name = "Event.findByCzasRozp", query = "SELECT e FROM Event e WHERE e.czasRozp = :czasRozp"),
    @NamedQuery(name = "Event.findByCzasZakon", query = "SELECT e FROM Event e WHERE e.czasZakon = :czasZakon")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nazwa", nullable = false, length = 45)
    private String nazwa;
    @Basic(optional = false)
    @Column(name = "czas_rozp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date czasRozp;
    @Basic(optional = false)
    @Column(name = "czas_zakon", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date czasZakon;
    @JoinColumn(name = "miejsce", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Miejsce miejsce;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, String nazwa, Date czasRozp, Date czasZakon) {
        this.id = id;
        this.nazwa = nazwa;
        this.czasRozp = czasRozp;
        this.czasZakon = czasZakon;
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

    public Date getCzasRozp() {
        return czasRozp;
    }

    public void setCzasRozp(Date czasRozp) {
        this.czasRozp = czasRozp;
    }

    public Date getCzasZakon() {
        return czasZakon;
    }

    public void setCzasZakon(Date czasZakon) {
        this.czasZakon = czasZakon;
    }

    public Miejsce getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(Miejsce miejsce) {
        this.miejsce = miejsce;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.waveq.meetme.entity.Event[ id=" + id + " ]";
    }
    
}

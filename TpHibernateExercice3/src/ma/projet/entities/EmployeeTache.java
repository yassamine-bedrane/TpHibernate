/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.entities;

import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;

/**
 *
 * @author yassa
 */
@Entity
public class EmployeeTache {

    @EmbeddedId
    private EmployeeTacheKey id;

    @ManyToOne
    @JoinColumn(name = "employee", referencedColumnName = "id", insertable = false, updatable = false)

    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "tache", referencedColumnName = "id", insertable = false, updatable = false)

    private Tache tache;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebutReelle;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeeTache() {
    }
    
    

}

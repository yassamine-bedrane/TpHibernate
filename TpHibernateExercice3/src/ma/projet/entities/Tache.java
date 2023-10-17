/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author yassa
 */
@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDebut;
    
    @OneToMany(mappedBy="tache")
    private List<EmployeeTache> employees;
    
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
    
}

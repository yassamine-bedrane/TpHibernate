/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author yassa
 */
@Entity
public class LigneCommandeProduit {
    
    @EmbeddedId
    private LigneCommandeProduitKey id;
    
    private int quantite;
    
    @ManyToOne
    @JoinColumn(name = "produit", referencedColumnName = "id", insertable = false, updatable = false)
    private Produit produit;
    
    @ManyToOne
    @JoinColumn(name = "commande", referencedColumnName = "id", insertable = false, updatable = false)
    private Commande commande;

    
    
    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(int quantite, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.commande = commande;
        this.produit = produit;
    }

    public LigneCommandeProduitKey getId() {
        return id;
    }

    public void setId(LigneCommandeProduitKey id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphibernateexercice2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil;

import ma.projet.entities.Produit;
import ma.projet.entities.Categorie;
import ma.projet.entities.Commande;
import ma.projet.entities.LigneCommandeProduit;
import ma.projet.service.LigneCommandeService;

/**
 *
 * @author yassa
 */
public class TpHibernateExercice2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CommandeService commandeService = new CommandeService();
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        LigneCommandeService lcs = new LigneCommandeService();

        Categorie categorie1 = new Categorie("Cat1", "Category 1");
        Categorie categorie2 = new Categorie("Cat2", "Category 2");

        categorieService.create(categorie1);
        categorieService.create(categorie2);

        Produit produit1 = new Produit("Prod1", 150.0f, categorie1);
        Produit produit2 = new Produit("Prod2", 200.0f, categorie2);

        produitService.create(produit1);
        produitService.create(produit2);

        Commande commande1 = new Commande(new Date());

        commandeService.create(commande1);

        LigneCommandeProduit ligne1 = new LigneCommandeProduit(5, commande1, produit1);
        LigneCommandeProduit ligne2 = new LigneCommandeProduit(10, commande1, produit2);
        
        lcs.create(ligne1);
        lcs.create(ligne2);
        lcs.create(new LigneCommandeProduit(12, commandeService.findById(1), produitService.findById(1)));

        // Test 1:
        Categorie c = categorieService.findById(1);
        List<Produit> produitsByCategory = produitService.getProduitParCategorie(c);
        System.out.println("Products in category " + c.getLibelle() + ":");
        for (Produit produit : produitsByCategory) {
            System.out.println(produit.getReference() + " - " + produit.getPrix() + " DH");
        }

        // Test 2: 
        Date date1 = parseDate("2023-09-01");
        Date date2 = parseDate("2023-09-30");
        List<Produit> produitsOrderedBetweenDates = produitService.getProduitsCommandesEntreDates(date1, date2);
        System.out.println("Products ordered between " + date1 + " and " + date2 + ":");
        for (Produit produit : produitsOrderedBetweenDates) {
            System.out.println(produit.getReference() + " - " + produit.getPrix() + " DH");
        }
        // Test 3: 
        Commande commande = commandeService.findById(1);
        produitService.displayProduitsInCommande(commande);

        // Test 4: 
        float minPrice = 100.0f;
        List<Produit> produitsWithPriceExceedingValue = produitService.getProduitsPrixSuperieur(minPrice);
        System.out.println("Products with price exceeding " + minPrice + " DH:");
        for (Produit produit : produitsWithPriceExceedingValue) {
            System.out.println(produit.getReference() + " - " + produit.getPrix() + " DH");
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}

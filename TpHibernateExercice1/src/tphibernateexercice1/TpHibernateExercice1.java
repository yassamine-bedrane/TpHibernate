/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphibernateexercice1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import ma.projet.entities.Produit;
import ma.projet.service.ProduitService;

/**
 *
 * @author yassa
 */
public class TpHibernateExercice1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ProduitService ps = new ProduitService();

        ps.create(new Produit("marque1", "ref1", new Date(), 123, "designation1"));
        ps.create(new Produit("marque2", "ref2", new Date(), 123, "designation2"));
        ps.create(new Produit("marque3", "ref3", new Date(), 12, "designation3"));
        ps.create(new Produit("marque4", "ref4", new Date(), 123, "designation4"));
        ps.create(new Produit("marque5", "ref5", new Date(), 12, "designation5"));

        System.out.print("Liste des produits: \n");
        for (Produit p : ps.findAll()) {
            System.out.print("Marque: " + p.getMarque() + "\n");
            System.out.print("Reference: " + p.getReference() + "\n");
            System.out.print("Designation: " + p.getDesignation() + "\n");
            System.out.print("Date: " + p.getDateAchat() + "\n");
            System.out.print("prix: " + p.getPrix() + "\n");
            System.out.print("__________________________________\n");
        }

        System.out.println("Produit id=2");
        Produit p2 = ps.findById(2);
        System.out.print("Marque: " + p2.getMarque() + "\n");
        System.out.print("Reference: " + p2.getReference() + "\n");
        System.out.print("Designation: " + p2.getDesignation() + "\n");
        System.out.print("Date: " + p2.getDateAchat() + "\n");
        System.out.print("prix: " + p2.getPrix() + "\n");
        
        
        ps.delete(ps.findById(3));
        
        Produit pu=new Produit("marque1 updated","ref1", new Date(), 123, "designation1");
        pu.setId(1);
        ps.update(pu);
        
        System.out.println("Produits avec prix >100: ");
        for (Produit p : ps.getProduitsPrixSuperieurA100()) {
            System.out.print("Marque: " + p.getMarque() + "\n");
            System.out.print("Reference: " + p.getReference() + "\n");
            System.out.print("Designation: " + p.getDesignation() + "\n");
            System.out.print("Date: " + p.getDateAchat() + "\n");
            System.out.print("prix: " + p.getPrix() + "\n");
            System.out.print("__________________________________\n");
        }
        
        
        System.out.println("Produits commandés entre 2 dates");
        
        for (Produit p : ps.getProduitsCommandesEntreDates(parseDate("2023-05-14"), parseDate("2023-11-13"))) {
            System.out.print("Marque: " + p.getMarque() + "\n");
            System.out.print("Reference: " + p.getReference() + "\n");
            System.out.print("Designation: " + p.getDesignation() + "\n");
            System.out.print("Date: " + p.getDateAchat() + "\n");
            System.out.print("prix: " + p.getPrix() + "\n");
            System.out.print("__________________________________\n");
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

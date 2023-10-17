/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Query;
import ma.projet.dao.IDao;
import ma.projet.entities.Produit;
import ma.projet.entities.Categorie;
import ma.projet.entities.Commande;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yassa
 */
public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Produit o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Produit> findAll() {
        List<Produit> produits = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produits = session.createQuery("from Produit").list();
            tx.commit();
            return produits;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            return produits;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Produit findById(int id) {
        Produit produit = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            produit = (Produit) session.get(Produit.class, id);
            tx.commit();
            return produit;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            return produit;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Produit> getProduitParCategorie(Categorie categorie) {
        Session session = null;
        List<Produit> produits = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            String hql = "from Produit p where p.categorie= :categorie";
            Query query = session.createQuery(hql);
            query.setParameter("categorie", categorie);

            produits = query.list();

            tx.commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return produits;
    }

    public List<Produit> getProduitsCommandesEntreDates(Date date1, Date date2) {
        Session session = null;
        List<Produit> produits = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            String hql = "select distinct p from Produit p "
                    + "inner join p.commandes lc "
                    + "where lc.commande.date between :date1 and :date2";

            Query query = session.createQuery(hql);
            query.setParameter("date1", date1);
            query.setParameter("date2", date2);

            produits = query.list();

            tx.commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return produits;
    }

    public void displayProduitsInCommande(Commande commande) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            String hql = "select lc.produit, lc.produit.prix, lc.quantite "
                    + "from LigneCommandeProduit lc "
                    + "where lc.commande = :commande";

            Query query = session.createQuery(hql);
            query.setParameter("commande", commande);

            List<Object[]> results = query.list();

            System.out.println("Commande : " + commande.getId() + " Date : " + commande.getDate());
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\tQuantité");

            for (Object[] row : results) {
                Produit produit = (Produit) row[0];
                float prix = (Float) row[1];
                int quantite = (Integer) row[2];

                System.out.println(produit.getReference() + "\t" + prix + " DH\t" + quantite);
            }

            tx.commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Produit> getProduitsPrixSuperieur(float prix) {
        Session session = null;
        Transaction tx = null;
        List<Produit> produits = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("Produit.findProduitsPrixSuperieur");
            query.setParameter("prix", prix);

            produits = query.list();

            tx.commit();
        } catch (HibernateException ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return produits;
    }

}

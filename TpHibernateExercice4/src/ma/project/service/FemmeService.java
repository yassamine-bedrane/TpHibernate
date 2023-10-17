/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.project.service;

import java.util.Date;
import java.util.List;
import ma.project.beans.Femme;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

/**
 *
 * @author yassa
 */
public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
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
    public List<Femme> getAll() {
        List<Femme> femmes = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            femmes = session.createQuery("from Femme").list();
            tx.commit();
            return femmes;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return femmes;

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Femme getById(int id) {
        Femme femme = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            femme = (Femme) session.get(Femme.class, id);
            tx.commit();
            return femme;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return femme;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Femme getFemmeLaPlusAgee() {
        Session session = null;
        Transaction tx = null;
        Femme femme = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Femme.class);
            criteria.addOrder(Order.asc("dateNaissance"));
            criteria.setMaxResults(1); 

            femme = (Femme) criteria.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return femme;
    }


    public int countEnfantsFemmeBetweenDates(int femmeId, Date dateDebut, Date dateFin) {
     Session session = HibernateUtil.getSessionFactory().openSession();
     session.beginTransaction();
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit-name");
     EntityManager em = emf.createEntityManager();
     try {
     Query query = em.createNamedQuery("countEnfantsFemmeBetweenDates",Femme.class)
     .setParameter("femmeId", femmeId)
     .setParameter("dateDebut", dateDebut)
     .setParameter("dateFin", dateFin);

     int nombreEnfants = (int) query.getSingleResult();

     session.getTransaction().commit();

     return nombreEnfants;
     } catch (Exception e) {
     session.getTransaction().rollback();
     throw e;
     }
     }

     /*public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit-name");
     EntityManager em = emf.createEntityManager();
     TypedQuery<Femme> query = em.createNamedQuery("findFemmesMarieesDeuxFoisOuPlus", Femme.class);
     return query.getResultList();
     } */
}

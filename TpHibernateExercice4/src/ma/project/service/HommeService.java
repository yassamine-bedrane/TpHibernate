/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.project.service;

import java.util.Date;
import java.util.List;
import ma.project.beans.Femme;
import ma.project.beans.Homme;
import ma.project.beans.Mariage;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NullPrecedence;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yassa
 */
public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
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
    public List<Homme> getAll() {
        List<Homme> hommes = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            hommes = session.createQuery("from Homme").list();
            tx.commit();
            return hommes;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return hommes;

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Homme getById(int id) {
        Homme homme = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            homme = (Homme) session.get(Homme.class, id);
            tx.commit();
            return homme;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return homme;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Femme> getEpousesBetweenDates(Homme homme, Date dateDebut, Date dateFin) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT f FROM Femme f JOIN f.mariages m "
                    + "WHERE m.homme = :homme "
                    + "AND (:dateDebut <= m.dateDebut AND :dateFin >= m.dateFin)";

            Query query;
            query = session.createQuery(hql);
            query.setParameter("homme", homme);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    public int countHommesMarriedToFourFemmesBetweenDates(Date startDate, Date endDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            Criteria criteria = session.createCriteria(Homme.class);
            criteria.createAlias("mariages", "m");
            criteria.add(Restrictions.between("m.dateMariage", startDate, endDate));
            criteria.setProjection(Projections.groupProperty("id"));
            criteria.add(Restrictions.sqlRestriction("count(distinct m.femme_id) >= 4"));

            int count = criteria.list().size();

            session.getTransaction().commit();

            return count;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void getMariagesHomme(Homme homme) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            Criteria criteria = session.createCriteria(Mariage.class);
            criteria.add(Restrictions.eq("homme", homme));
            criteria.addOrder(Order.asc("dateFin").nulls(NullPrecedence.FIRST));

            List<Mariage> mariages = criteria.list();

            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
            System.out.println("Mariages En Cours :");

            for (Mariage mariage : mariages) {
                if (mariage.getDateFin() == null) {
                    printMariage(mariage);
                }
            }

            System.out.println("Mariages échoués :");

            for (Mariage mariage : mariages) {
                if (mariage.getDateFin() != null) {
                    printMariage(mariage);
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    private void printMariage(Mariage mariage) {
        System.out.println("Femme : " + mariage.getFemme().getNom() + " " + mariage.getFemme().getPrenom());
        System.out.println("Date Début : " + mariage.getDateDebut());
        if (mariage.getDateFin() != null) {
            System.out.println("Date Fin : " + mariage.getDateFin());
        }
        System.out.println("Nbr Enfants : " + mariage.getNbrEnfants());
    }

}

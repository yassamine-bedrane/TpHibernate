/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphibernateexercice4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.project.beans.Femme;
import ma.project.beans.Homme;
import ma.project.beans.Mariage;
import ma.project.service.FemmeService;
import ma.project.service.HommeService;
import ma.project.service.MariageService;
import ma.project.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author yassa
 */
public class TpHibernateExercice4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        Homme homme1 = new Homme("Mohammed", "Ali", "0601000001", "Rabat, Morocco", parseDate("1980-01-15"));
        hommeService.create(homme1);

        Homme homme2 = new Homme("Ahmed", "Hassan", "0601000002", "Casablanca, Morocco", parseDate("1975-05-20"));
        hommeService.create(homme2);

        Homme homme3 = new Homme("Khalid", "Said", "0601000003", "Marrakech, Morocco", parseDate("1983-09-10"));
        hommeService.create(homme3);

        Homme homme4 = new Homme("Youssef", "Abdallah", "0601000004", "Fes, Morocco", parseDate("1978-04-05"));
        hommeService.create(homme4);

        Homme homme5 = new Homme("Omar", "Moussa", "0601000005", "Tangier, Morocco", parseDate("1982-02-28"));
        hommeService.create(homme5);

        Femme femme1 = new Femme("Fatima", "Amine", "0602000001", "Rabat, Morocco", parseDate("1985-03-25"));
        femmeService.create(femme1);

        Femme femme2 = new Femme("Amina", "Karim", "0602000002", "Casablanca, Morocco", parseDate("1979-07-12"));
        femmeService.create(femme2);

        Femme femme3 = new Femme("Laila", "Hassan", "0602000003", "Marrakech, Morocco", parseDate("1981-11-30"));
        femmeService.create(femme3);

        Femme femme4 = new Femme("Karima", "Said", "0602000004", "Fes, Morocco", parseDate("1984-06-19"));
        femmeService.create(femme4);

        Femme femme5 = new Femme("Houda", "Abdallah", "0602000005", "Tangier, Morocco", parseDate("1980-09-08"));
        femmeService.create(femme5);

        Femme femme6 = new Femme("Nadia", "Youssef", "0602000006", "Rabat, Morocco", parseDate("1983-12-07"));
        femmeService.create(femme6);

        Femme femme7 = new Femme("Samira", "Omar", "0602000007", "Casablanca, Morocco", parseDate("1986-04-16"));
        femmeService.create(femme7);

        Femme femme8 = new Femme("Fatiha", "Moussa", "0602000008", "Marrakech, Morocco", parseDate("1982-08-03"));
        femmeService.create(femme8);

        Femme femme9 = new Femme("Sanaa", "Hassan", "0602000009", "Fes, Morocco", parseDate("1977-10-21"));
        femmeService.create(femme9);

        Femme femme10 = new Femme("Naima", "Ali", "0602000010", "Tangier, Morocco", parseDate("1984-05-14"));
        femmeService.create(femme10);

        for (Femme f : femmeService.getAll()) {
            System.out.print(f.getNom() + " " + f.getPrenom() + "\n");
        }
        Femme fa = femmeService.getFemmeLaPlusAgee();
        System.out.println("La femme la plus agée: " + fa.getNom() + " " + fa.getPrenom() + " " + fa.getDateNaissance());

        
        
        mariageService.create(new Mariage(parseDate("2002-05-14"), parseDate("2006-05-14"), 2, hommeService.getById(1), femmeService.getById(6)));
        mariageService.create(new Mariage(parseDate("2003-08-20"), parseDate("2009-12-15"), 3, hommeService.getById(2), femmeService.getById(7)));
        mariageService.create(new Mariage(parseDate("2001-03-10"), parseDate("2005-07-28"), 1, hommeService.getById(3), femmeService.getById(8)));
        mariageService.create(new Mariage(parseDate("2004-11-05"), parseDate("2007-09-22"), 2, hommeService.getById(4), femmeService.getById(9)));
        mariageService.create(new Mariage(parseDate("2000-07-18"), parseDate("2008-06-30"), 4, hommeService.getById(5), femmeService.getById(10)));
        mariageService.create(new Mariage(parseDate("2005-02-12"), parseDate("2010-11-17"), 3, hommeService.getById(1), femmeService.getById(8)));
        mariageService.create(new Mariage(parseDate("2007-09-30"), parseDate("2012-03-25"), 2, hommeService.getById(2), femmeService.getById(9)));
        mariageService.create(new Mariage(parseDate("2006-04-22"), parseDate("2011-08-10"), 1, hommeService.getById(3), femmeService.getById(10)));

        
        List<Femme> epouses=hommeService.getEpousesBetweenDates(hommeService.getById(1), parseDate("2002-05-14"), parseDate("2010-11-17"));
        
        System.out.print("Epouses de "+hommeService.getById(1).getNom()+"\n");
        for (Femme f:epouses){
            System.out.print(f.getNom()+" "+f.getPrenom()+"\n");
        }
        
        System.out.print("nbr d'enfants "+femmeService.countEnfantsFemmeBetweenDates(10, parseDate("2000-07-18"), parseDate("2008-06-30")));
        
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

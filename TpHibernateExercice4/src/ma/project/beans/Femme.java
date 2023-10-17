/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.project.beans;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author yassa
 */
@Entity
@NamedNativeQuery(
        name = "countEnfantsFemmeBetweenDates",
        query = "SELECT m.nbrEnfants "
        + "FROM mariage m "
        + "INNER JOIN femme f ON f.id = m.femme_id "
        + "WHERE m.dateDebut >= :dateDebut "
        + "  AND m.dateFin <= :dateFin "
        + "  AND f.id = :femme_id"
)

/*@NamedQueries({
 @NamedQuery(
 name = "Femme.findFemmesMarieesDeuxFoisOuPlus",
 query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
 )
 })*/
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.EAGER)
    private List<Mariage> mariages;

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

}

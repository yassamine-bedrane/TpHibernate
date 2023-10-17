/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author yassa
 */
@Embeddable
public class EmployeeTacheKey implements Serializable{
    private int employee;
    private int tache;

    public EmployeeTacheKey() {
    }

    public EmployeeTacheKey(int employee, int tache) {
        this.employee = employee;
        this.tache = tache;
    }
    
    
    
}

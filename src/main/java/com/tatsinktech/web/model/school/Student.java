/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.school;

import com.tatsinktech.web.model.AbstractModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author olivier.tatsinkou
 */

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "student")
public class Student extends AbstractModel<Long>{
    
   @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;

    @Column(name = "student_name")
    private String studentName;
    
    // list of msisdn allow to get information of student (e.g 65852364;569886566665;9665533654)
    @Column(name = "listMsisdn")
    private String listMsisdn;
    
  
    @OneToMany(mappedBy = "student")
    private Set<RelStudent_Evaluation> listRelStudent_Evaluation = new HashSet<>();
}

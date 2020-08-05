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
@Table(name = "schoolaryCycle")
public class SchoolaryCycle extends AbstractModel<Long> {

    @Column(name = "cycle_code", nullable = false, unique = true)
    private String cycleCode;

    @Column(name = "school_year")
    private String schoolYear;

    @OneToMany(mappedBy = "matiere")
    private Set<Matiere> listMatiere = new HashSet<>();
}

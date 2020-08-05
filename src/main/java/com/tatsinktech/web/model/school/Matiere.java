/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.school;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(name = "matiere",uniqueConstraints={@UniqueConstraint(columnNames = {"matiere_code","schoolLevel_id","schoolaryCycle_id"})})
public class Matiere extends AbstractModel<Long> {

    @Column(name = "matiere_code", nullable = false, unique = true)
    private String matiereCode;

    @Column(name = "matiere_name")
    private String matiereName;

    @Column(name = "school_year")
    private String schoolYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolLevel_id", nullable = true)
    private SchoolLevel schoolLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schoolaryCycle_id", nullable = true)
    private SchoolaryCycle schoolaryCycle;
}

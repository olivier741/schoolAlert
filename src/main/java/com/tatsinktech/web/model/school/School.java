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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "school")
public class School extends AbstractModel<Long> {

    @Column(name = "school_code", nullable = false, unique = true)
    private String schoolCode;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "addresss")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_school", nullable = true)
    private TypeSchool typeSchool;

    // list of role allow to get information on that school (e.g admin | manager | teacher_school_akwa
    @Column(name = "role_allow")
    private String roleAllow;
    
    @OneToMany(mappedBy = "school")
    private Set<Departement> listDepartement = new HashSet<>();
}

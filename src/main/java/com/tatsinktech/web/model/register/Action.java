/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "action",uniqueConstraints={@UniqueConstraint(columnNames = {"action_type","product_id"})})
public class Action extends AbstractModel<Long>  {

    @Column(name = "action_name",nullable = false, unique = true)
    private String actionName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = true)
    private Action_Type actionType;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
    
    @OneToMany(mappedBy = "action")
    private Set<Command> listCommand = new HashSet<>();
    
}

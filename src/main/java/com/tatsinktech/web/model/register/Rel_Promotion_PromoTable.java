/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
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
@Table(name = "rel_promotion_promotable", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"promotable_id", "promotion_id"})})
public class Rel_Promotion_PromoTable extends AbstractModel<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotable_id", nullable = true)
    private PromoTable promotable;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id", nullable = true)
    private Promotion promotion;

}

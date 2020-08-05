/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.pushsms;

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
@Table(name = "phone_push",uniqueConstraints={@UniqueConstraint(columnNames = {"msisdn","push_hist_id"})})
public class PhonePhush extends AbstractModel<Long>{
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "push_hist_id", nullable = true)
    private PushSMS push_hist;
}

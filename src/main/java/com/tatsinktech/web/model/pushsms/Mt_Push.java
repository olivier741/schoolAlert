/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.pushsms;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "mt_push")
public class Mt_Push extends AbstractModel<Long>{
   
    @Column(name = "message_id",nullable = true)
    private String messageid;
    
    @Column(name = "sender",nullable = true)
    private String sender;
    
    @Column(name = "receiver",nullable = true)
    private String receiver;
    
    @Column(name = "delivery_id",nullable = true)
    private String deliveryID;
    
    @Column(name = "delivery_sub",nullable = true)
    private String deliverySub;
    
    @Column(name = "delivery_dlvrd",nullable = true)
    private String deliveryDlvrd;
    
    @Column(name = "delivery_submitDate",nullable = true)
    private String deliverySubmitDate;
    
    @Column(name = "delivery_doneDate",nullable = true)
    private String deliveryDoneDate;
    
    @Column(name = "delivery_status",nullable = true)
    private String deliveryStatus;
    
    @Column(name = "delivery_err",nullable = true)
    private String deliveryErr;
    
    @Column(name = "delivery_text",nullable = true)
    private String deliveryText;

}

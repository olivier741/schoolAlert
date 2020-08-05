/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.school;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "mo_push")
public class Mo_Push extends AbstractModel<Long>{
    
    @Column(name = "channel",nullable = false)
    private String sendChannel;
    
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @Column(name = "push_name", nullable = false)
    private String pushName;
    
    @Lob
    @Column(name = "message")
    private String message;
    
    @Column(name = "operator",nullable = false)
    private String operator;
    
    @Column(name = "product_code",nullable = false)
    private String productCode;
        
    @Column(name = "service_name",nullable = false)
    private String serviceName;
    
    
}

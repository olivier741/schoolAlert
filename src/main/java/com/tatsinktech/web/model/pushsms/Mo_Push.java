/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.pushsms;

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
    private String channel;
    
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @Lob
    @Column(name = "message")
    private String message;
    
    @Column(name = "operator",nullable = false)
    private String operator;
    
    @Column(name = "service_name",nullable = false)
    private String service_name;
    
    
}

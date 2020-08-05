/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
@Table(name = "ws_accessManagement",uniqueConstraints={@UniqueConstraint(columnNames = {"ws_client_id","ws_webservice_id"})})
public class WS_AccessManagement extends AbstractModel<Long> {


    @Column(name = "access_name",nullable = false, unique = true)
    private String accessName;
    
    @Column(name = "max_connexion_allocate",nullable = true)
    private long maxConnexionAllocate;
    
    @Column(name = "max_request_allocate",nullable = true)
    private long maxRequestAllocate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time",nullable = true)
    private Date startTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time",nullable = true)
    private Date endTime;

       
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ws_client_id", nullable = true)
    private WS_Client wsClient;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ws_webservice_id", nullable = true)
    private WS_Webservice wsWebservice;
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "ws_transaction_log",
        indexes = {
                @Index(columnList = "msisdn", name = "msisdn_wstranslog_idx"),
                @Index(columnList = "transaction_id", name = "transaction_wstranslog_idx")
        })
public class WS_Transaction_Log extends AbstractModel<Long> {

     @Column(name = "transaction_id",nullable = false)
    private String transactionId;
    
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @Column(name = "operator",nullable = true)
    private String operator;
    
    @Column(name = "client_name",nullable = true)
    private String clientName;
    
    @Column(name = "webservice_name",nullable = true)
    private String webserviceName;
    
     @Column(name = "accessManagement_name",nullable = true)
    private String accessManagement_name;
    
    @Column(name = "ipAddress",nullable = true)
    private String ipAddress;
    
    @Column(name = "processUnit",nullable = true)
    private String processUnit;
     
    @Lob 
    @Column(name = "request",nullable = true)
    private String request;
    
    @Lob 
    @Column(name = "response",nullable = true)
    private String response;
    
    @Column(name = "duration",nullable = true)
    private long duration;
     
    @Column(name = "error_code",nullable = true)
    private String errorCode;
    
    @Column(name = "translate_error_code",nullable = true)
    private String translateErrorCode;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time", nullable = true)
    private Date requestTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "response_time", nullable = true)
    private Date responseTime;
        
  
}

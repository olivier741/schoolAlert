/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
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
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "ws_billing_Hist",
        indexes = {
                @Index(columnList = "msisdn", name = "msisdn_wsbilling_idx"),
                @Index(columnList = "transaction_id", name = "transaction_wsbilling_idx")
        })
public class WS_Billing_Hist extends AbstractModel<Long>{

    @Column(name = "transaction_id",nullable = false)
    private String transactionId;
    
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @Column(name = "operator",nullable = false)
    private String operator;
    
    @Column(name = "client_name",nullable = false)
    private String clientName;
    
    @Column(name = "webservice_name",nullable = false)
    private String webserviceName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_changing",nullable = false)
    private Type_Charging typeChanging;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_account",nullable = false)
    private Type_Account typeAccount;
    
    @Column(name = "charge_time",nullable = false)
    private Timestamp chargeTime;
    
    @Column(name = "charge_fee",nullable = false)
    private long chargeFee;
    
    @Lob 
    @Column(name = "charge_request",nullable = false)
    private String chargeRequest;
    
    @Lob 
    @Column(name = "charge_response",nullable = false)
    private String chargeResponse;
    
    @Column(name = "duration",nullable = false)
    private long duration;
     
    @Column(name = "charge_error",nullable = false)
    private String chargeError;

}

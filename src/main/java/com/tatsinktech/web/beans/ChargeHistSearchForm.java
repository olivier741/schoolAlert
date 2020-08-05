/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.beans;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author olivier
 */
@Setter
@Getter
@NoArgsConstructor
public class ChargeHistSearchForm {
    private String transactionId;

    private String msisdn;

    private int trans_option;
    
    private int msisdn_option;
    
    private boolean isChargeTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date charge_dateFrom;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date charge_dateTo;

    public boolean isIsChargeTime() {
        return isChargeTime;
    }

    public void setIsChargeTime(boolean isChargeTime) {
        this.isChargeTime = isChargeTime;
    }

   
    
}

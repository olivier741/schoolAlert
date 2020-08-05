package com.tatsinktech.web.beans;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegisterSearchForm {

    private String transactionId;

    private String msisdn;

    private int trans_option;
    
    private int msisdn_option;
    
    private boolean isRegTime;
    
    private boolean isRenewTime;
    
    private boolean isExpTime;
    
    private boolean isUnregTime;
    
    private boolean isCancelTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reg_dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reg_dateTo;
    
     @Temporal(TemporalType.TIMESTAMP)
    private Date renew_dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date renew_dateTo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date exp_dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exp_dateTo;
    
     @Temporal(TemporalType.TIMESTAMP)
    private Date unreg_dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date unreg_dateTo;
    
     @Temporal(TemporalType.TIMESTAMP)
    private Date cancel_dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date cancel_dateTo;

    public boolean isIsRegTime() {
        return isRegTime;
    }

    public void setIsRegTime(boolean isRegTime) {
        this.isRegTime = isRegTime;
    }

    public boolean isIsRenewTime() {
        return isRenewTime;
    }

    public void setIsRenewTime(boolean isRenewTime) {
        this.isRenewTime = isRenewTime;
    }

    public boolean isIsExpTime() {
        return isExpTime;
    }

    public void setIsExpTime(boolean isExpTime) {
        this.isExpTime = isExpTime;
    }

    public boolean isIsUnregTime() {
        return isUnregTime;
    }

    public void setIsUnregTime(boolean isUnregTime) {
        this.isUnregTime = isUnregTime;
    }

    public boolean isIsCancelTime() {
        return isCancelTime;
    }

    public void setIsCancelTime(boolean isCancelTime) {
        this.isCancelTime = isCancelTime;
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.pushsms;

import com.tatsinktech.web.model.AbstractModel;
import com.tatsinktech.web.model.register.ServiceProvider;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
 * @author olivier.tatsinkou
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "push_sms")
public class PushSMS extends AbstractModel<Long> {

    @Column(name = "push_name", nullable = false, unique = true)
    private String pushName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "push_level", nullable = true)
    private PushLevel pushLevel;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "submit_user_id", nullable = true)
    private String submitUserID;

    @Column(name = "validate_user_id", nullable = true)
    private String validateUserID;

    @Column(name = "confirm_user_id", nullable = true)
    private String confirmUserID;

    //  2019-04-16 23:00:01-07:00:00  this offer will launch  the 2019-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "validate_time", nullable = true)
    public Date validateTime;

    //  2019-04-16 23:00:01-07:00:00  this offer will launch  the 2019-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirm_time", nullable = true)
    public Date confirmteTime;
    
     //  2019-04-16 23:00:01-07:00:00  this offer will launch  the 2019-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = true)
    public Date startTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = true)
    private ServiceProvider service;

}

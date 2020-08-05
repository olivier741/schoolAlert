/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

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
@Table(name = "ws_response",uniqueConstraints={@UniqueConstraint(columnNames = {"response_code","ws_webservice_id"})})
public class WS_ResponseAPI extends AbstractModel<Long>{
    
    @Column(name = "response_code",nullable = false)
    private String responseCode;
    
    @Column(name = "translate_response_code",nullable = true)
    private int translateResponseCode;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ws_webservice_id", nullable = true)
    private WS_Webservice wsWebservice;
    
}

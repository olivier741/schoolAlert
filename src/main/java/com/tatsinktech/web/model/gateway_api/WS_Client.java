/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@Table(name = "ws_client")
public class WS_Client extends AbstractModel<Long> {

    @Column(name = "client_name", nullable = false, unique = true)
    private String clientName;

    @Column(name = "login", nullable = true)
    private String login;

    @Column(name = "isResetpassword", nullable = true)
    private Boolean isResetpassword;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "password_salt", nullable = true)
    private String passwordSalt;

    @Column(name = "ip_address", nullable = true)
    private String ipAddress;

    @Column(name = "tps_allow", nullable = true)
    private long tpsAllow;

    @OneToMany(mappedBy = "wsClient")
    private Set<WS_AccessManagement> listAccessManagement = new HashSet<>();


}

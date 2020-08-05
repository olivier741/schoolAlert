/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ws_webservice")
public class WS_Webservice extends AbstractModel<Long> {

    @Column(name = "webservice_name", nullable = false, unique = true)
    private String webserviceName;

    @Column(name = "api_url", nullable = false)
    private String apiUrl;

    @Column(name = "webservice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type_API webserviceType;

    @Column(name = "api_rest_uri", nullable = true)
    private String apiRestUri;

    @Column(name = "api_rest_method", nullable = true)
    @Enumerated(EnumType.STRING)
    private Rest_Method apiRestMethod;

    @Column(name = "rest_media_type", nullable = true)
    private String restMediaType;

    @Column(name = "content_type", nullable = true)
    private String contentType;

    @Column(name = "rest_bind_param", nullable = true)
    @Enumerated(EnumType.STRING)
    private Bind_Parameter restBindParam;

    @Column(name = "api_rest_request", nullable = true)
    @Lob
    private String apiRestRequest;

    @Column(name = "api_soap_wsdl", nullable = true)
    @Lob
    private String apiSoapWsdl;

    @Column(name = "api_soap_xsd", nullable = true)
    @Lob
    private String apiSoapXsd;

    @Column(name = "api_soap_request_header", nullable = true)
    @Lob
    private String apiSoapRequestHeader;

    @Column(name = "api_soap_request_body", nullable = true)
    @Lob
    private String apiSoapRequestBody;

    @Column(name = "remote_operator", nullable = true)
    private String remoteOperator;

    @Column(name = "remote_con_timeout", nullable = true)
    private long remoteConTimeout;

    @Column(name = "remote_req_timeout", nullable = true)
    private long remoteReqTimeout;

    @Column(name = "remote_max_connexion", nullable = true)
    private long remoteMaxConnexion;

    @Column(name = "remote_max_request", nullable = true)
    private long remoteMaxRequest;

    @Column(name = "remote_secutity_mode", nullable = true)
    @Enumerated(EnumType.STRING)
    private Security_Mode remoteSecutityMode;

    @Column(name = "security_login", nullable = true)
    private String securityLogin;

    @Column(name = "security_password", nullable = true)
    private String securityPassword;

    @Column(name = "authorizationType", nullable = true)
    private String authorizationType;

    @Column(name = "token", nullable = true)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_time_token", nullable = true)
    private Date expireTimeToken;

    @OneToMany(mappedBy = "wsWebservice")
    private Set<WS_AccessManagement> listWS_AccessManagement = new HashSet<>();

    @OneToMany(mappedBy = "wsWebservice")
    private Set<WS_ResponseAPI> listWS_Response = new HashSet<>();

}

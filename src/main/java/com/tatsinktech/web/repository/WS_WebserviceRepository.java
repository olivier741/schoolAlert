/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.gateway_api.WS_Client;
import com.tatsinktech.web.model.gateway_api.WS_Webservice;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */

@Repository
public interface WS_WebserviceRepository extends PagingAndSortingRepository<WS_Webservice, String> {
    WS_Webservice findWS_WebserviceById(long id);
    Page<WS_Webservice>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<WS_Webservice>findByWebserviceNameContainingIgnoreCase(String webserviceName, Pageable pageable);
    Page<WS_Webservice>findByApiUrlContainingIgnoreCase(String apiUrl, Pageable pageable);
    Page<WS_Webservice>findByWebserviceTypeContainingIgnoreCase(String webserviceType, Pageable pageable);
    Page<WS_Webservice>findByApiRestUriContainingIgnoreCase(String apiRestUri, Pageable pageable);
    Page<WS_Webservice>findByApiRestMethodContainingIgnoreCase(String apiRestMethod, Pageable pageable);
    Page<WS_Webservice>findByRestMediaTypeContainingIgnoreCase(String restMediaType, Pageable pageable);
    Page<WS_Webservice>findByContentTypeContainingIgnoreCase(String contentType, Pageable pageable);
    Page<WS_Webservice>findByRestBindParamContainingIgnoreCase(String restBindParam, Pageable pageable);
    Page<WS_Webservice>findByApiRestRequestContainingIgnoreCase(String apiRestRequest, Pageable pageable);
    Page<WS_Webservice>findByApiSoapWsdlContainingIgnoreCase(String apiSoapWsdl, Pageable pageable);
    Page<WS_Webservice>findByApiSoapXsdContainingIgnoreCase(String apiSoapXsd, Pageable pageable);
    
    Page<WS_Webservice>findByApiSoapRequestHeaderContainingIgnoreCase(String apiSoapRequest, Pageable pageable);
    Page<WS_Webservice>findByApiSoapRequestBodyContainingIgnoreCase(String apiSoapRequest, Pageable pageable);
    
    Page<WS_Webservice>findByRemoteOperatorContainingIgnoreCase(String remoteOperator, Pageable pageable);
    Page<WS_Webservice>findByRemoteConTimeoutContainingIgnoreCase(long remoteConTimeout, Pageable pageable);
    Page<WS_Webservice>findByRemoteReqTimeoutContainingIgnoreCase(long remoteReqTimeout, Pageable pageable);
    Page<WS_Webservice>findByRemoteMaxConnexionContainingIgnoreCase(long remoteMaxConnexion, Pageable pageable);
    Page<WS_Webservice>findByRemoteMaxRequestContainingIgnoreCase(long remoteMaxRequest, Pageable pageable);
    Page<WS_Webservice>findByRemoteSecutityModeContainingIgnoreCase(String remoteSecutityMode, Pageable pageable);
    Page<WS_Webservice>findBySecurityLoginContainingIgnoreCase(String securityLogin, Pageable pageable);
    Page<WS_Webservice>findBySecurityPasswordContainingIgnoreCase(String securityPassword, Pageable pageable);
    Page<WS_Webservice>findByTokenContainingIgnoreCase(String token, Pageable pageable);
    Page<WS_Webservice>findByExpireTimeTokenContainingIgnoreCase(Date expireTimeToken, Pageable pageable);
    

    Page<WS_Webservice>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<WS_Webservice>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<WS_Webservice>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
 
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.gateway_api.WS_Client;
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
public interface WS_ClientRepository extends PagingAndSortingRepository<WS_Client, String> {
    WS_Client findWS_ClientById(long id);
    Page<WS_Client>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<WS_Client>findByClientNameContainingIgnoreCase(String clientName, Pageable pageable);
    Page<WS_Client>findByLoginContainingIgnoreCase(String login, Pageable pageable);
    Page<WS_Client>findByIsResetpasswordContainingIgnoreCase(boolean isresetPass, Pageable pageable);
    Page<WS_Client>findByPasswordContainingIgnoreCase(String password, Pageable pageable);
    Page<WS_Client>findByPasswordSaltContainingIgnoreCase(String passwordSalt, Pageable pageable);
    Page<WS_Client>findByIpAddressContainingIgnoreCase(String ip_address, Pageable pageable);
    Page<WS_Client>findByTpsAllowContainingIgnoreCase(long tpsAllow, Pageable pageable);
    
    Page<WS_Client>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<WS_Client>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<WS_Client>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
 
}
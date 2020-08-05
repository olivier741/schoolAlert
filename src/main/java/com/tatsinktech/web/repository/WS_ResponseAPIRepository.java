/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.gateway_api.WS_ResponseAPI;
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
public interface WS_ResponseAPIRepository extends PagingAndSortingRepository<WS_ResponseAPI, String> {
    WS_ResponseAPI findWS_ResponseById(long id);
    Page<WS_ResponseAPI>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<WS_ResponseAPI>findByTranslateResponseCodeContainingIgnoreCase(String translateResponseCode, Pageable pageable);
    Page<WS_ResponseAPI>findByResponseCodeContainingIgnoreCase(String responseCode, Pageable pageable);
    
    Page<WS_ResponseAPI>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<WS_ResponseAPI>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<WS_ResponseAPI>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
 
}
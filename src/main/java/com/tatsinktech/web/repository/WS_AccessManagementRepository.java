/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.gateway_api.WS_AccessManagement;
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
public interface WS_AccessManagementRepository extends PagingAndSortingRepository<WS_AccessManagement, String> {
    WS_AccessManagement findWS_AccessManagementById(long id);
    Page<WS_AccessManagement>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<WS_AccessManagement>findByAccessNameContainingIgnoreCase(String accessName, Pageable pageable);
    Page<WS_AccessManagement>findByMaxConnexionAllocateContainingIgnoreCase(long maxConnexionAllocate, Pageable pageable);
    Page<WS_AccessManagement>findByMaxRequestAllocateContainingIgnoreCase(long maxRequestAllocate, Pageable pageable);
    Page<WS_AccessManagement>findByStartTimeContainingIgnoreCase(Date startTime, Pageable pageable);
    Page<WS_AccessManagement>findByEndTimeContainingIgnoreCase(Date endTime, Pageable pageable);
    
    Page<WS_AccessManagement>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<WS_AccessManagement>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<WS_AccessManagement>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
 
}
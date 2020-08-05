/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.ServiceProvider;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */


@Repository
public interface ServiceProviderRepository extends PagingAndSortingRepository<ServiceProvider, String> {
    ServiceProvider findServiceProviderById(long id);
    Page<ServiceProvider>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<ServiceProvider>findByServiceNameContainingIgnoreCase(String service_name, Pageable pageable);
    Page<ServiceProvider>findByReceiveChannelContainingIgnoreCase(String receive_channel, Pageable pageable);
    Page<ServiceProvider>findBySendChannelContainingIgnoreCase(String send_channel, Pageable pageable);
    Page<ServiceProvider>findByServiceProviderContainingIgnoreCase(String service_provider, Pageable pageable);
    Page<ServiceProvider>findByOperatorContainingIgnoreCase(String operator, Pageable pageable);
    
    Page<ServiceProvider>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<ServiceProvider>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<ServiceProvider>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.pushsms.ServicePush;
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
public interface ServicePushRepository extends PagingAndSortingRepository<ServicePush, String>{
    ServicePush findServicePushById(long id);
    ServicePush findServicePushByServiceName(String service_name);
    Page<ServicePush>findByIdContainingIgnoreCase(long id, Pageable pageable);
    Page<ServicePush>findByServiceNameContainingIgnoreCase(String service_name, Pageable pageable);

    Page<ServicePush>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<ServicePush>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<ServicePush>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
}

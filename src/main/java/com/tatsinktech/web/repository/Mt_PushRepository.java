/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.pushsms.Mt_Push;
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
public interface Mt_PushRepository extends PagingAndSortingRepository<Mt_Push, String> {

    Mt_Push findServiceProviderById(long id);

    Page<Mt_Push> findByIdContainingIgnoreCase(long id, Pageable pageable);

    Page<Mt_Push> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Mt_Push> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<Mt_Push> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
}

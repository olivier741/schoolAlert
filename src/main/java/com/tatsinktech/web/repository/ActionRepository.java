/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Action;
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
public interface ActionRepository extends PagingAndSortingRepository<Action, String> {
    Action findActionById(long id);
    Page<Action>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<Action>findByActionNameContainingIgnoreCase(String action_name, Pageable pageable);
    Page<Action>findByActionTypeContainingIgnoreCase(String action_type, Pageable pageable);
    
    Page<Action>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Action>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<Action>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    
}

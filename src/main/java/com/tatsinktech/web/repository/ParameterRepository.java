/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Parameter;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */
//@Repository
//public interface ParameterRepository extends JpaRepository<Parameter, Long> {
//
//}
//

@Repository
public interface ParameterRepository extends PagingAndSortingRepository<Parameter, String> {
    Parameter findParameterById(long id);
    Page<Parameter>findByIdContainingIgnoreCase(long id, Pageable pageable);
    Page<Parameter>findByParamNameContainingIgnoreCase(String param_name, Pageable pageable);
    Page<Parameter>findByParamLengthContainingIgnoreCase(long param_length, Pageable pageable);
    Page<Parameter>findByParamPatternContainingIgnoreCase(String param_pattern, Pageable pageable);
    
    Page<Parameter>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Parameter>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<Parameter>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    
}

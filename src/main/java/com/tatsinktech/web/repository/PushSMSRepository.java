/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.pushsms.PushLevel;
import com.tatsinktech.web.model.pushsms.PushSMS;
import java.util.Collection;
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
public interface PushSMSRepository extends PagingAndSortingRepository<PushSMS, String> {

    PushSMS findPushSMSById(long id);

    PushSMS findPushSMSByPushName(String push_name);

//    Page<PushSMS> findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<PushSMS> findByPushNameContainingIgnoreCaseAndPushLevelIn(String push_name, Collection<PushLevel>  level,Pageable pageable);
    
    Page<PushSMS> findByPushLevelIn(Collection<PushLevel> level, Pageable pageable);

    Page<PushSMS> findByPushNameContainingIgnoreCaseAndSubmitUserIDAndPushLevelIn(String push_name,String submit_id, Collection<PushLevel>  level,Pageable pageable);
    
    Page<PushSMS> findBySubmitUserIDAndPushLevelIn(String submit_id,Collection<PushLevel> level, Pageable pageable);
    
//    Page<PushSMS> findByPushLevelContainingIgnoreCase(String pushLevel, Pageable pageable);
//    
//     Page<PushSMS> findByValidateTimeContainingIgnoreCase(Date validateTime, Pageable pageable);
//
//    Page<PushSMS> findByConfirmteTimeContainingIgnoreCase(Date confirmteTime, Pageable pageable);
//    
//    Page<PushSMS> findByStartTimeContainingIgnoreCase(Date startTime, Pageable pageable);
//
//    Page<PushSMS> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
//
//    Page<PushSMS> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
//
//    Page<PushSMS> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
}

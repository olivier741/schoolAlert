/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Promotion;
import com.tatsinktech.web.model.register.ServiceProvider;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */


@Repository
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, String> {
    Promotion findPromotionById(long id);
    Page<Promotion>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<Promotion>findByPromotionNameContainingIgnoreCase(String promotion_name, Pageable pageable);
    Page<Promotion>findByPromotionFilterContainingIgnoreCase(String promotion_filter, Pageable pageable);
    Page<Promotion>findByIsTableSelectedContainingIgnoreCase(boolean msisdn_table, Pageable pageable);
    Page<Promotion>findByMsisdnRegexContainingIgnoreCase(String msisdn_regex, Pageable pageable);
    Page<Promotion>findByStartTimeContainingIgnoreCase(Date startTime, Pageable pageable);
    Page<Promotion>findByEndTimeContainingIgnoreCase(Date endTime, Pageable pageable);
    Page<Promotion>findByReductionModeContainingIgnoreCase(String reductionMode, Pageable pageable);
    Page<Promotion>findBypromotionRegFeeContainingIgnoreCase(long promotionRegFee, Pageable pageable);
    Page<Promotion>findByPercentageRegContainingIgnoreCase(long percentageReg, Pageable pageable);
    Page<Promotion>findByIsExtendContainingIgnoreCase(boolean isExtend, Pageable pageable);
    Page<Promotion>findByPromotionExtFeeContainingIgnoreCase(long promotionExtFee, Pageable pageable);
    Page<Promotion>findByPromotionRegFeeContainingIgnoreCase(long promotionRegFee, Pageable pageable);
    Page<Promotion>findByPercentageExtContainingIgnoreCase(long percentageExt, Pageable pageable);
    
    Page<Promotion>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Promotion>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<Promotion>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    
}
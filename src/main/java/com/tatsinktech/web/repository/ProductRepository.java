/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Product;
import com.tatsinktech.web.model.register.Promotion;
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
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    Product findProductById(long id);
    Page<Product>findByIdContainingIgnoreCase(long id, Pageable pageable);
    
    Page<Product>findByProductCodeContainingIgnoreCase(String product_code, Pageable pageable);
    Page<Product>findByRegFeeContainingIgnoreCase(long reg_fee, Pageable pageable);
    Page<Product>findByRestrictProductContainingIgnoreCase(String restrict_product, Pageable pageable);
    Page<Product>findByStartTimeContainingIgnoreCase(Date startTime, Pageable pageable);
    Page<Product>findByEndTimeContainingIgnoreCase(Date endTime, Pageable pageable);
    Page<Product>findByIsFrameValidityContainingIgnoreCase(boolean isFrameValidity, Pageable pageable);
    Page<Product>findByRestrictConstantValidityContainingIgnoreCase(String restrictConstantValidity, Pageable pageable);
    Page<Product>findByFrameTimeValidityContainingIgnoreCase(String frameTimeValidity, Pageable pageable);
    Page<Product>findByValidityContainingIgnoreCase(String validity, Pageable pageable);
    Page<Product>findByPendingDurationContainingIgnoreCase(String pendingDuration, Pageable pageable);
    Page<Product>findByIsExtendContainingIgnoreCase(boolean isExtend, Pageable pageable);
    Page<Product>findByIsOverideRegContainingIgnoreCase(boolean isOverideReg, Pageable pageable);
    Page<Product>findByIsNotifyExtendContainingIgnoreCase(boolean isNotifyExtend, Pageable pageable);
    Page<Product>findByExtendFeeContainingIgnoreCase(long extendFee, Pageable pageable);

//    
    Page<Product>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Product>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<Product>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    
}
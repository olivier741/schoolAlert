/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.PromoTable;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface PromoTableRepository extends PagingAndSortingRepository<PromoTable, String> {

    PromoTable findPromoTableById(long id);

    Page<PromoTable> findByIdContainingIgnoreCase(long id, Pageable pageable);

    Page<PromoTable> findByTableNameContainingIgnoreCase(String tableName, Pageable pageable);

    Page<PromoTable> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<PromoTable> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<PromoTable> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
//    @Query("CREATE TABLE :tableName  (MSISDN VARCHAR2(100) NOT NULL UNIQUE ")
//    void createTablePromotion(String tableName);
//    
//    @Query("INSERT INTO :tableName (MSISDN) VALUES ( :msisdn )")
//    void addTablePromotion(String tableName, String msisdn);
//    
//    @Query("UPDATE :tableName tab SET tab.msisdn = :msisdn_new WHERE  tab.msisdn = :msisdn_old ")
//    void updateTablePromotion(String tableName, String msisdn_old,String msisdn_new);
//    
//    @Query("DELETE FROM :tableName tab WHERE  tab.msisdn = :msisdn ")
//    void deleteTablePromotion(String tableName, String msisdn);
//    
//    @Query("SELECT tab.msisdn FROM :tableName tab")
//    List<String> getTablePromotion();
//    
////    @Query("SELECT tab.msisdn FROM :tableName tab WHERE  msisdn LIKE '%:msisdn%' ")
////    List<String> getTablePromotionByMsisdn(String msisdn);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Parameter;
import com.tatsinktech.web.model.register.Register;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */
@Repository
public interface RegisterRepository extends PagingAndSortingRepository<Register, Long> {

    Register findRegisterById(long id);

    Page<Register> findByMsisdnContainingIgnoreCase(String msisdn, Pageable pageable);

    Page<Register> findByMsisdnIgnoreCase(String msisdn, Pageable pageable);

    Page<Register> findByTransactionIdContainingIgnoreCase(String transactionId, Pageable pageable);

    Page<Register> findByTransactionIdIgnoreCase(String transactionId, Pageable pageable);

    Page<Register> findByStatusContainingIgnoreCase(int status, Pageable pageable);

    Page<Register> findByAutoextendContainingIgnoreCase(boolean autoextend, Pageable pageable);

    Page<Register> findByRegTimeContainingIgnoreCase(Date reg_time, Pageable pageable);

    Page<Register> findByRenewTimeContainingIgnoreCase(Date renewTime, Pageable pageable);

    Page<Register> findByExpireTimeContainingIgnoreCase(Date expireTime, Pageable pageable);

    Page<Register> findByUnregTimeContainingIgnoreCase(Date unregTime, Pageable pageable);

    Page<Register> findByCancelTimeContainingIgnoreCase(Date cancelTime, Pageable pageable);

    Page<Register> findByNumberRegContainingIgnoreCase(long numberReg, Pageable pageable);

    Page<Register> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Register> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<Register> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    // ------------------

    Page<Register> findByMsisdnContainingIgnoreCaseAndTransactionIdContainingIgnoreCase(String msisdn,String transID, Pageable pageable);

    Page<Register> findByMsisdnIgnoreCaseAndTransactionIdContainingIgnoreCase(String msisdn,String transID, Pageable pageable);

    Page<Register> findByMsisdnContainingIgnoreCaseAndTransactionIdIgnoreCase(String msisdn,String transID, Pageable pageable);

    Page<Register> findByMsisdnIgnoreCaseAndTransactionIdIgnoreCase(String msisdn,String transID, Pageable pageable);

    // search repository by RegTime
    Page<Register> findByRegTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    // search repository by Renew
    Page<Register> findByRenewTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    // search repository by Expire
    Page<Register> findByExpireTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    // search repository by Unreg
    Page<Register> findByUnregTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    // search repository by Cancel
    Page<Register> findByCancelTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew
    Page<Register> findByRegTimeBetweenAndRenewTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Expire
    Page<Register> findByRegTimeBetweenAndExpireTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Unreg
    Page<Register> findByRegTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Cancel
    Page<Register> findByRegTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Expire
    Page<Register> findByRenewTimeBetweenAndExpireTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Unreg
    Page<Register> findByRenewTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Cancel
    Page<Register> findByRenewTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by Expire and Unreg
    Page<Register> findByExpireTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by ExpireTime and Cancel
    Page<Register> findByExpireTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by UnregTime and Cancel
    Page<Register> findByUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Expire
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Unreg
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Cancel
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Expire and Unreg
    Page<Register> findByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Expire and Cancel
    Page<Register> findByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Unreg and Cancel
    Page<Register> findByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Expire and Unreg
    Page<Register> findByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Expire and Cancel
    Page<Register> findByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Unreg and Cancel
    Page<Register> findByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by ExpireTime and Unreg and Cancel
    Page<Register> findByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Expire and Unreg
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Expire and Cancel
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    // search repository by RenewTime and Unreg and Unreg and Cancel 
    Page<Register> findByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, String transactionId, String msisdn, Pageable pageable);

    // search repository by RegTime and Renew and Expire and Unreg and Cancel
    Page<Register> findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start1, Date End1, Date Start2, Date End2, Date Start3, Date End3, Date Start4, Date End4, Date Start5, Date End5, String transactionId, String msisdn, Pageable pageable);

}

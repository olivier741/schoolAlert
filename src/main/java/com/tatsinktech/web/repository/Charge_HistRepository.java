/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Charge_Hist;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface Charge_HistRepository extends PagingAndSortingRepository<Charge_Hist, Long> {

    Charge_Hist findCharge_HistById(long id);

    Page<Charge_Hist> findByMsisdnContainingIgnoreCase(String msisdn, Pageable pageable);

    Page<Charge_Hist> findByMsisdnIgnoreCase(String msisdn, Pageable pageable);

    Page<Charge_Hist> findByTransactionIdContainingIgnoreCase(String transactionId, Pageable pageable);

    Page<Charge_Hist> findByTransactionIdIgnoreCase(String transactionId, Pageable pageable);

    Page<Charge_Hist> findByChargeModeContainingIgnoreCase(String chargeMode, Pageable pageable);

    Page<Charge_Hist> findByChargeTimeContainingIgnoreCase(Date reg_time, Pageable pageable);

    Page<Charge_Hist> findByChargeFeeContainingIgnoreCase(long chargeFee, Pageable pageable);

    Page<Charge_Hist> findByChargeRequestContainingIgnoreCase(String chargeRequest, Pageable pageable);

    Page<Charge_Hist> findByChargeResponseContainingIgnoreCase(String chargeResponse, Pageable pageable);

    Page<Charge_Hist> findByDurationContainingIgnoreCase(long duration, Pageable pageable);

    Page<Charge_Hist> findByChargeErrorContainingIgnoreCase(String chargeError, Pageable pageable);

    Page<Charge_Hist> findByProcessUnitContainingIgnoreCase(String processUnit, Pageable pageable);

    Page<Charge_Hist> findByIpAddressContainingIgnoreCase(String IpAddress, Pageable pageable);

    // ------------------
    Page<Charge_Hist> findByMsisdnContainingIgnoreCaseAndTransactionIdContainingIgnoreCase(String msisdn, String transID, Pageable pageable);

    Page<Charge_Hist> findByMsisdnIgnoreCaseAndTransactionIdContainingIgnoreCase(String msisdn, String transID, Pageable pageable);

    Page<Charge_Hist> findByMsisdnContainingIgnoreCaseAndTransactionIdIgnoreCase(String msisdn, String transID, Pageable pageable);

    Page<Charge_Hist> findByMsisdnIgnoreCaseAndTransactionIdIgnoreCase(String msisdn, String transID, Pageable pageable);

    // search repository by ChargeTime
    Page<Charge_Hist> findByChargeTimeBetween(Date Start, Date End, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndMsisdnContainingIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndMsisdnIgnoreCase(Date Start, Date End, String msisdn, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdContainingIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdIgnoreCase(Date Start, Date End, String transactionId, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

    Page<Charge_Hist> findAllByChargeTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date Start, Date End, String transactionId, String msisdn, Pageable pageable);

}

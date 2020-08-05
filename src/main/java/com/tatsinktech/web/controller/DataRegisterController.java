/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.beans.RegisterSearchForm;
import com.tatsinktech.web.model.register.Register;
import com.tatsinktech.web.repository.RegisterRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("registers")
public class DataRegisterController {

    private boolean enableEdit = true;

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    @Autowired
    private RegisterRepository registerRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @PostMapping("/list")
    public ModelMap postList(@PageableDefault(size = 10) Pageable pageable,
            @Valid @ModelAttribute("searchForm") RegisterSearchForm searchForm, Model model,
            @NotNull Authentication auth
    ) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date reg_dateFrom = searchForm.getReg_dateFrom();
            Date reg_dateTo = searchForm.getReg_dateTo();

            Date renew_dateFrom = searchForm.getRenew_dateFrom();
            Date renew_dateTo = searchForm.getRenew_dateTo();

            Date exp_dateFrom = searchForm.getExp_dateFrom();
            Date exp_dateTo = searchForm.getExp_dateTo();

            Date unreg_dateFrom = searchForm.getUnreg_dateFrom();
            Date unreg_dateTo = searchForm.getUnreg_dateTo();

            Date cancel_dateFrom = searchForm.getRenew_dateFrom();
            Date cancel_dateTo = searchForm.getRenew_dateTo();

            int trans_option = searchForm.getTrans_option();
            int msisdn_option = searchForm.getMsisdn_option();

            boolean isreg = searchForm.isIsRegTime();
            boolean isrenew = searchForm.isIsRenewTime();
            boolean iscancel = searchForm.isIsCancelTime();
            boolean isexp = searchForm.isIsExpTime();
            boolean isunreg = searchForm.isIsUnregTime();

            if (!isreg && !isrenew && !iscancel && !isexp && !isunreg) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCase(msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCase(msisdn, pageable));
                    }

                }

                if (!StringUtils.isBlank(transID) && StringUtils.isBlank(msisdn)) {
                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdIgnoreCase(transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByTransactionIdContainingIgnoreCase(transID, pageable));
                    }

                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {
                    if (trans_option == 2 && msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCaseAndTransactionIdIgnoreCase(msisdn,transID, pageable));
                                                                                       
                    }

                    if (trans_option == 2 && msisdn_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCaseAndTransactionIdIgnoreCase(msisdn,transID, pageable));
                    }

                    if (trans_option != 2 && msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnIgnoreCaseAndTransactionIdContainingIgnoreCase(msisdn,transID, pageable));
                    }

                    if (trans_option != 2 && msisdn_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findByMsisdnContainingIgnoreCaseAndTransactionIdContainingIgnoreCase(msisdn,transID, pageable));
                    }

                }

            }

            // **********************one true **********************************
            // filter by reg_time
            if (isreg && !isrenew && !isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetween(reg_dateFrom, reg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew_time
            if (!isreg && isrenew && !isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetween(renew_dateFrom, renew_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID) && renew_dateFrom != null && renew_dateTo != null) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by expire time
            if (!isreg && !isrenew && isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByExpireTimeBetween(exp_dateFrom, exp_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdIgnoreCase(exp_dateFrom, exp_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCase(exp_dateFrom, exp_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by unreg_time
            if (!isreg && !isrenew && !isexp && isunreg && !iscancel) {
                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByUnregTimeBetween(unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by cancel time
            if (!isreg && !isrenew && !isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByCancelTimeBetween(cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndMsisdnIgnoreCase(cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndMsisdnContainingIgnoreCase(cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            //****************************two true***************************************
            // filter by reg_time and renew time
            if (isreg && isrenew && !isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    model.addAttribute("searchForm", searchForm);
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg_time and expire time
            if (isreg && !isrenew && isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    model.addAttribute("searchForm", searchForm);
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndExpireTimeBetween(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg_time and unreg time
            if (isreg && !isrenew && !isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndUnregTimeBetween(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg_time and cancel time
            if (isreg && !isrenew && !isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    model.addAttribute("searchForm", searchForm);
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndCancelTimeBetween(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew_time and expire time
            if (!isreg && isrenew && isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndExpireTimeBetween(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew_time and unreg time
            if (!isreg && isrenew && !iscancel && !isexp && isunreg) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndUnregTimeBetween(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {
                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew_time and cancel time
            if (!isreg && isrenew && !isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndCancelTimeBetween(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by expire_time and unreg time
            if (!isreg && !isrenew && isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByExpireTimeBetweenAndUnregTimeBetween(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by expire_time and cancel time
            if (!isreg && !isrenew && isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByExpireTimeBetweenAndCancelTimeBetween(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by unreg time and cancel time
            if (!isreg && !isrenew && !isexp && isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByUnregTimeBetweenAndCancelTimeBetween(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and expire time
            if (isreg && isrenew && isexp && !isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and unreg time
            if (isreg && isrenew && !isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and cancel time
            if (isreg && isrenew && !isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and expire time and unreg time
            if (isreg && !isrenew && isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and expire time and cancel time
            if (isreg && !isrenew && isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew time and expire time and unreg time
            if (!isreg && isrenew && isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew time and expire time and cancel time
            if (!isreg && isrenew && isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew time and unreg time and cancel time
            if (!isreg && isrenew && !isexp && isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by expire time and unreg time and cancel time
            if (!isreg && !isrenew && isexp && isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and expire time and unreg time
            if (isreg && isrenew && isexp && isunreg && !iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and expire time and cancel time
            if (isreg && isrenew && isexp && !isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by renew time and expire time and unreg time and cancel time
            if (!isreg && isrenew && isexp && isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            // filter by reg time and renew time and expire time and unreg time and cancel time
            if (isreg && isrenew && isexp && isunreg && iscancel) {

                if (StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    result = new ModelMap().addAttribute("registers", registerRepo.findByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetween(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, pageable));
                }

                if (!StringUtils.isBlank(msisdn) && StringUtils.isBlank(transID)) {
                    if (msisdn_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, msisdn, pageable));
                    }

                }

                if (StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, pageable));
                    }
                }

                if (!StringUtils.isBlank(msisdn) && !StringUtils.isBlank(transID)) {

                    if (msisdn_option == 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option == 2 && trans_option != 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else if (msisdn_option != 2 && trans_option == 2) {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    } else {
                        result = new ModelMap().addAttribute("registers", registerRepo.findAllByRegTimeBetweenAndRenewTimeBetweenAndExpireTimeBetweenAndUnregTimeBetweenAndCancelTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(reg_dateFrom, reg_dateTo, renew_dateFrom, renew_dateTo, exp_dateFrom, exp_dateTo, unreg_dateFrom, unreg_dateTo, cancel_dateFrom, cancel_dateTo, transID, msisdn, pageable));
                    }
                }
            }

            model.addAttribute("searchForm", searchForm);
        } else {
            RegisterSearchForm searchForm1 = new RegisterSearchForm();
            searchForm1.setCancel_dateFrom(new Date());
            searchForm1.setCancel_dateTo(new Date());
            searchForm1.setExp_dateFrom(new Date());
            searchForm1.setExp_dateTo(new Date());
            searchForm1.setReg_dateFrom(new Date());
            searchForm1.setReg_dateTo(new Date());
            searchForm1.setRenew_dateFrom(new Date());
            searchForm1.setRenew_dateTo(new Date());

            searchForm1.setTrans_option(1);
            searchForm1.setMsisdn_option(1);
            searchForm1.setIsCancelTime(false);
            searchForm1.setIsExpTime(false);
            searchForm1.setIsRegTime(false);
            searchForm1.setIsRenewTime(false);
            searchForm1.setIsUnregTime(false);
            model.addAttribute("searchForm", searchForm1);
        }

        return result;
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model,
            @NotNull Authentication auth
    ) {
        loadMode(model, auth);
        Register entity = registerRepo.findRegisterById(id);
        enableEdit = true;

        String productCode = "";
        if (entity.getProduct() != null) {
            productCode = entity.getProduct().getProductCode();
        }

        model.addAttribute("product_code", productCode);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("reg", entity);
    }

    @GetMapping("/list")
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") RegisterSearchForm searchForm, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("registers", registerRepo.findAll(pageable));

        RegisterSearchForm searchForm1 = new RegisterSearchForm();
        searchForm1.setCancel_dateFrom(new Date());
        searchForm1.setCancel_dateTo(new Date());
        searchForm1.setExp_dateFrom(new Date());
        searchForm1.setExp_dateTo(new Date());
        searchForm1.setReg_dateFrom(new Date());
        searchForm1.setReg_dateTo(new Date());
        searchForm1.setRenew_dateFrom(new Date());
        searchForm1.setRenew_dateTo(new Date());

        searchForm1.setTrans_option(1);
        searchForm1.setMsisdn_option(1);
        searchForm1.setIsCancelTime(false);
        searchForm1.setIsExpTime(false);
        searchForm1.setIsRegTime(false);
        searchForm1.setIsRenewTime(false);
        searchForm1.setIsUnregTime(false);
        model.addAttribute("searchForm", searchForm1);

        return result;
    }

    private void loadMode(Model model, Authentication auth) {
        AccessToken acceToken = ((SimpleKeycloakAccount) auth.getDetails())
                .getKeycloakSecurityContext()
                .getToken();
        String id = acceToken.getId();
        String username = acceToken.getPreferredUsername();
        String birtdate = acceToken.getBirthdate();
        String name = acceToken.getName();
        String email = acceToken.getEmail();
        String firstName = acceToken.getFamilyName();
        String genre = acceToken.getGender();
        String givenName = acceToken.getGivenName();
        String midleName = acceToken.getMiddleName();
        String nickName = acceToken.getNickName();
        String phone_number = acceToken.getPhoneNumber();

        model.addAttribute("keycloak_username", username);
        model.addAttribute("keycloak_id", id);
        model.addAttribute("keycloak_lastname", firstName);
        model.addAttribute("keycloak_name", name);
        model.addAttribute("keycloak_email", email);
        model.addAttribute("keycloak_firstname", givenName);
        model.addAttribute("keycloak_genre", genre);
        model.addAttribute("keycloak_midlename", midleName);
        model.addAttribute("keycloak_nickname", nickName);
        model.addAttribute("keycloak_phone", phone_number);
    }
}

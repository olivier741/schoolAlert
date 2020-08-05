/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.beans.ChargeHistSearchForm;
import com.tatsinktech.web.beans.RegisterSearchForm;
import com.tatsinktech.web.repository.Charge_HistRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("chargehists")
public class DataCharge_HistController {
     private boolean enableEdit = true;

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    @Autowired
    private Charge_HistRepository chargeHistRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @PostMapping("/list")
    public ModelMap postList(@PageableDefault(size = 10) Pageable pageable,
            @Valid @ModelAttribute("searchForm") ChargeHistSearchForm searchForm, Model model,
            @NotNull Authentication auth
    ) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("chargehists", chargeHistRepo.findAll(pageable));
        if (searchForm != null) {
            String msisdn = searchForm.getMsisdn();
            String transID = searchForm.getTransactionId();
            Date charge_dateFrom = searchForm.getCharge_dateFrom();
            Date charge_dateTo = searchForm.getCharge_dateTo();
            int trans_option = searchForm.getTrans_option();
            int msisdn_option = searchForm.getMsisdn_option();
            boolean ischarge = searchForm.isIsChargeTime();
        }
        
         return result;
    }
    
    
     @GetMapping("/list")
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable, @Valid @ModelAttribute("searchForm") ChargeHistSearchForm searchForm, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ModelMap result = new ModelMap().addAttribute("chargehists", chargeHistRepo.findAll(pageable));

        ChargeHistSearchForm searchForm1 = new ChargeHistSearchForm();
        searchForm1.setCharge_dateFrom(new Date());
        searchForm1.setCharge_dateTo(new Date());
        searchForm1.setTrans_option(1);
        searchForm1.setMsisdn_option(1);
        searchForm1.setIsChargeTime(false);
       
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

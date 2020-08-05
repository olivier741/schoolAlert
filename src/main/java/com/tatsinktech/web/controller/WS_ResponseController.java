/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.gateway_api.WS_ResponseAPI;
import com.tatsinktech.web.model.gateway_api.WS_Webservice;
import com.tatsinktech.web.repository.WS_WebserviceRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.tatsinktech.web.repository.WS_ResponseAPIRepository;

/**
 *
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("wsresponses")
public class WS_ResponseController {
     private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, WS_Webservice> HashWS_Webservice = new HashMap<String, WS_Webservice>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private WS_ResponseAPIRepository wsresponseRepo;

    @Autowired
    private WS_WebserviceRepository wserviceRepo;

    public boolean isEnableSave() {
        return enableSave;
    }

    public void setEnableSave(boolean enableSave) {
        this.enableSave = enableSave;
    }

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    @GetMapping("/list")
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable,
            @RequestParam(name = "value", required = false) String value,
            Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        if (value != null) {
            model.addAttribute("key", value);
            return new ModelMap().addAttribute("wsresponses", wsresponseRepo.findByResponseCodeContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("wsresponses", wsresponseRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_ResponseAPI entity = wsresponseRepo.findWS_ResponseById(id);
        enableEdit = true;

        Iterable<WS_Webservice> listWS_Webservice = wserviceRepo.findAll();

        HashWS_Webservice.clear();
        for (WS_Webservice websrv : listWS_Webservice) {
            HashWS_Webservice.put(websrv.getWebserviceName(), websrv);
        }
        
        model.addAttribute("listWS_Webservice", listWS_Webservice);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wsresp", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_ResponseAPI entity = new WS_ResponseAPI();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = wsresponseRepo.findWS_ResponseById(id);
        }

        Iterable<WS_Webservice> listWS_Webservice = wserviceRepo.findAll();


        HashWS_Webservice.clear();
        for (WS_Webservice websrv : listWS_Webservice) {
            HashWS_Webservice.put(websrv.getWebserviceName(), websrv);
        }

        model.addAttribute("listWS_Webservice", listWS_Webservice);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("wsresp", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("wsresp") WS_ResponseAPI entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "/wsresponses/form";
        if (errors.hasErrors()) {

            is_error = true;

        }

        String websrv_name = entity.getWsWebservice().getWebserviceName();

        WS_Webservice webserv = null;

        if (!StringUtils.isBlank(websrv_name) && !websrv_name.equals("NONE")) {
            webserv = HashWS_Webservice.get(websrv_name);
        }

        entity.setWsWebservice(webserv);

        wsresponseRepo.save(entity);
        status.setComplete();
        result = "redirect:/wsresponses/list";

        if (is_error) {
            Iterable<WS_Webservice> listWS_Webservice = wserviceRepo.findAll();

            HashWS_Webservice.clear();
            for (WS_Webservice websrv : listWS_Webservice) {
                HashWS_Webservice.put(websrv.getWebserviceName(), websrv);
            }
            
            model.addAttribute("listWS_Webservice", listWS_Webservice);
        }

        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_ResponseAPI entity = wsresponseRepo.findWS_ResponseById(id);
        enableEdit = true;

        Iterable<WS_Webservice> listWS_Webservice = wserviceRepo.findAll();

        HashWS_Webservice.clear();
        for (WS_Webservice websrv : listWS_Webservice) {
            HashWS_Webservice.put(websrv.getWebserviceName(), websrv);
        }

        model.addAttribute("listWS_Webservice", listWS_Webservice);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wsresp", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("wsresp") WS_ResponseAPI entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            wsresponseRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getResponseCode())
                    .addObject("entityName", "wsresponses")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/wsresponses/list");
        }
        status.setComplete();
        return "redirect:/wsresponses/list";
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.gateway_api.WS_Client;
import com.tatsinktech.web.model.gateway_api.WS_Webservice;
import com.tatsinktech.web.repository.WS_WebserviceRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

/**
 *
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("wswebservices")
public class WS_WebserviceController {

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private WS_WebserviceRepository wsWebserviceRepo;

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
    public ModelMap getlist(@PageableDefault(size = 10) Pageable pageable, @RequestParam(name = "value", required = false) String value, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        if (value != null) {
            model.addAttribute("key", value);
            return new ModelMap().addAttribute("wswebservices", wsWebserviceRepo.findByWebserviceNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("wswebservices", wsWebserviceRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Webservice entity = wsWebserviceRepo.findWS_WebserviceById(id);
        enableEdit = true;

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wsrv", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Webservice entity = new WS_Webservice();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = wsWebserviceRepo.findWS_WebserviceById(id);
        }

        model.addAttribute("enableSave", enableSave);

        return new ModelMap("wsrv", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("wsrv") WS_Webservice entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        if (errors.hasErrors()) {
            return "wswebservices/form";
        }

        wsWebserviceRepo.save(entity);
        status.setComplete();
        return "redirect:/wswebservices/list";

    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Webservice entity = wsWebserviceRepo.findWS_WebserviceById(id);
        enableEdit = true;

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wsrv", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("wsrv") WS_Webservice entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            wsWebserviceRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getWebserviceName())
                    .addObject("entityName", "wswebservices")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/wswebservices/list");
        }
        status.setComplete();
        return "redirect:/wswebservices/list";
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

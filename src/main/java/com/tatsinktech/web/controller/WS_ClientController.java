/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.encrypt.PasswordUtils;
import com.tatsinktech.web.model.gateway_api.WS_Client;
import com.tatsinktech.web.model.register.Parameter;
import com.tatsinktech.web.repository.WS_ClientRepository;
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
@RequestMapping("wsclients")
public class WS_ClientController {

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private WS_ClientRepository wsClientRepo;

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
            return new ModelMap().addAttribute("wsclients", wsClientRepo.findByClientNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("wsclients", wsClientRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Client entity = wsClientRepo.findWS_ClientById(id);
        enableEdit = true;

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wcl", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Client entity = new WS_Client();
        enableSave = true;
        if (id != null) {

            enableSave = false;
            entity = wsClientRepo.findWS_ClientById(id);
        }

        model.addAttribute("enableSave", enableSave);

        return new ModelMap("wcl", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("wcl") WS_Client entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        if (errors.hasErrors()) {
            return "wsclients/form";
        }
        String salt="";
        String password="";
        if (entity.getIsResetpassword()!= null && entity.getIsResetpassword()) {
            salt = PasswordUtils.getSalt(64);
            password = PasswordUtils.generateSecurePassword(entity.getPassword(), salt);
            entity.setPassword(password);
            entity.setPasswordSalt(salt);
        } else {
            if (entity.getId() != null) {
                WS_Client ws_client = wsClientRepo.findWS_ClientById(entity.getId());
                entity.setPassword(ws_client.getPassword());
                entity.setPasswordSalt(ws_client.getPasswordSalt());
            } else {
                salt = PasswordUtils.getSalt(64);
                password = PasswordUtils.generateSecurePassword(entity.getPassword(), salt);
                entity.setPassword(password);
                entity.setPasswordSalt(salt);
            }
        }
        entity.setIsResetpassword(false);
        wsClientRepo.save(entity);
        status.setComplete();
        return "redirect:/wsclients/list";

    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        WS_Client entity = wsClientRepo.findWS_ClientById(id);
        enableEdit = true;

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("wcl", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("wcl") WS_Client entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            wsClientRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getClientName())
                    .addObject("entityName", "wsclients")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/wsclients/list");
        }
        status.setComplete();
        return "redirect:/wsclients/list";
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

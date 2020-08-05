/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.encrypt.PasswordUtils;
import com.tatsinktech.web.model.register.ServiceProvider;
import com.tatsinktech.web.repository.ServiceProviderRepository;
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
 * @author olivier
 */
@Controller
@RequestMapping("services")
public class ServiceProviderController {

    private boolean enableSave = true;
    private boolean enableEdit = true;
    private String respMessage = "";

    @Autowired
    private ServiceProviderRepository serviceRepo;

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

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
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
            return new ModelMap().addAttribute("services", serviceRepo.findByServiceNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("services", serviceRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        ServiceProvider entity = serviceRepo.findServiceProviderById(id);

        enableEdit = true;
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("srv", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ServiceProvider entity = new ServiceProvider();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = serviceRepo.findServiceProviderById(id);
        } else {
            respMessage = "Form to Create new Service";
        }

        model.addAttribute("enableSave", enableSave);
        model.addAttribute("respMessage", respMessage);

        return new ModelMap("srv", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("srv") ServiceProvider entity, @RequestParam(value = "action", required = true) String action,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        if (errors.hasErrors()) {
            respMessage = "Cannot Create this Service";
            model.addAttribute("respMessage", respMessage);
            return "services/form";
        }

        String salt = "";
        String providerID = "";
        if (entity.getIsResetProviderID()!=null && entity.getIsResetProviderID()) {
            salt = PasswordUtils.getSalt(64);
            providerID = PasswordUtils.generateSecurePassword(entity.getServiceProviderID(), salt);
            entity.setServiceProviderID(providerID);
            entity.setServiceProviderSalt(salt);
        } else {
            if (entity.getId() != null) {
                ServiceProvider service_provider = serviceRepo.findServiceProviderById(entity.getId());
                entity.setServiceProviderID(service_provider.getServiceProviderID());
                entity.setServiceProviderSalt(service_provider.getServiceProviderSalt());
            } else {
                salt = PasswordUtils.getSalt(64);
                providerID = PasswordUtils.generateSecurePassword(entity.getServiceProviderID(), salt);
                entity.setServiceProviderID(providerID);
                entity.setServiceProviderSalt(salt);
            }
        }
        entity.setIsResetProviderID(false);

        serviceRepo.save(entity);
        status.setComplete();
        return "redirect:/services/list";

    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        ServiceProvider entity = serviceRepo.findServiceProviderById(id);

        enableEdit = true;
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("srv", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("srv") ServiceProvider entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            serviceRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            respMessage = "Cannot Delete this Service";
            model.addAttribute("respMessage", respMessage);
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getServiceName())
                    .addObject("entityName", "services")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/services/list");
        }
        status.setComplete();
        return "redirect:/services/list";
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

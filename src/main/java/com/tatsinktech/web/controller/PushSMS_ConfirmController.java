/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.pushsms.PushLevel;
import com.tatsinktech.web.model.pushsms.PushSMS;
import com.tatsinktech.web.model.register.ServiceProvider;
import com.tatsinktech.web.repository.PushSMSRepository;
import com.tatsinktech.web.repository.ServiceProviderRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("pushsms_confirm")
public class PushSMS_ConfirmController {
     private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, ServiceProvider> HashService = new HashMap<String, ServiceProvider>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private PushSMSRepository pushSMSRepo;

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
        List<PushLevel> listLevel = new ArrayList<PushLevel>();
        listLevel.add(PushLevel.VALIDATE);
        listLevel.add(PushLevel.CONFIRM);
        listLevel.add(PushLevel.CANCEL);
        if (value != null) {
            model.addAttribute("key", value);
            return new ModelMap().addAttribute("pushsms_confirm", pushSMSRepo.findByPushNameContainingIgnoreCaseAndPushLevelIn(value, listLevel, pageable));
        } else {
            return new ModelMap().addAttribute("pushsms_confirm", pushSMSRepo.findByPushLevelIn(listLevel, pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        PushSMS entity = pushSMSRepo.findPushSMSById(id);
        enableEdit = true;

        Iterable<ServiceProvider> listService = serviceRepo.findAll();
        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getServiceName(), serv);

        }

        model.addAttribute("listService", listService);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("phsms", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {

        loadMode(model, auth);
        PushSMS entity = pushSMSRepo.findPushSMSById(id);
        enableEdit = true;

        Iterable<ServiceProvider> listService = serviceRepo.findAll();
        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getServiceName(), serv);

        }

        model.addAttribute("listService", listService);
        model.addAttribute("enableEdit", enableEdit);

        return new ModelMap("phsms", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("phsms") PushSMS entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        AccessToken acceToken = ((SimpleKeycloakAccount) auth.getDetails())
                .getKeycloakSecurityContext()
                .getToken();
        String user_id = acceToken.getPreferredUsername();

        loadMode(model, auth);

        boolean is_error = false;
        String result = "pushsms_confirm/form";
        if (errors.hasErrors()) {

            is_error = true;

        }
        String serv_name = entity.getService().getServiceName();

        ServiceProvider serv = null;

        if (!StringUtils.isBlank(serv_name) && !serv_name.equals("NONE")) {
            serv = HashService.get(serv_name);
        }

        entity.setService(serv);
        entity.setSubmitUserID(user_id);
        entity.setPushLevel(PushLevel.CONFIRM);
        entity.setConfirmteTime(new Date());
        
        pushSMSRepo.save(entity);
        status.setComplete();
        result = "redirect:/pushsms_confirm/list";

        if (is_error) {
            Iterable<ServiceProvider> listService = serviceRepo.findAll();
            HashService.clear();
            for (ServiceProvider srv : listService) {     
                    HashService.put(srv.getServiceName(), srv);
            }

            model.addAttribute("listService", listService);
            result = "pushsms_confirm/form";
        }

        return result;
    }

    @GetMapping("/cancel")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        
        loadMode(model, auth);
        PushSMS entity = pushSMSRepo.findPushSMSById(id);
        enableEdit = true;

        Iterable<ServiceProvider> listService = serviceRepo.findAll();
        HashService.clear();
        for (ServiceProvider serv : listService) {
          
                HashService.put(serv.getServiceName(), serv);
        }

        model.addAttribute("listService", listService);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("phsms", entity);
    }

    @PostMapping("/cancel")
    public Object postDelete(@Valid @ModelAttribute("phsms") PushSMS entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        AccessToken acceToken = ((SimpleKeycloakAccount) auth.getDetails())
                .getKeycloakSecurityContext()
                .getToken();
        String user_id = acceToken.getPreferredUsername();

        loadMode(model, auth);

        boolean is_error = false;
        String result = "pushsms_confirm/form";
        if (errors.hasErrors()) {

            is_error = true;

        }
        String serv_name = entity.getService().getServiceName();

        ServiceProvider serv = null;

        if (!StringUtils.isBlank(serv_name) && !serv_name.equals("NONE")) {
            serv = HashService.get(serv_name);
        }

        entity.setService(serv);
        entity.setSubmitUserID(user_id);
        entity.setPushLevel(PushLevel.CANCEL);
        entity.setConfirmteTime(new Date());
        pushSMSRepo.save(entity);
        status.setComplete();
        result = "redirect:/pushsms_confirm/list";

        if (is_error) {
            Iterable<ServiceProvider> listService = serviceRepo.findAll();
            HashService.clear();
            for (ServiceProvider srv : listService) {
                    HashService.put(srv.getServiceName(), srv);
            }

            model.addAttribute("listService", listService);
            result = "pushsms_validate/form";
        }

        return "redirect:/pushsms_confirm/list";
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

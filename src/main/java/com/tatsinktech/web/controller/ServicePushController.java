/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.pushsms.ServicePush;
import com.tatsinktech.web.model.register.ServiceProvider;
import com.tatsinktech.web.repository.ServiceProviderRepository;
import com.tatsinktech.web.repository.ServicePushRepository;
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
@RequestMapping("servicepushs")
public class ServicePushController {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private HashMap<String, ServiceProvider> HashService = new HashMap<String, ServiceProvider>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private ServicePushRepository servicePushRepo;

    @Autowired
    private ServiceProviderRepository serviceProviderRepo;

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
            return new ModelMap().addAttribute("servicepushs", servicePushRepo.findByServiceNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("servicepushs", servicePushRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ServicePush entity = servicePushRepo.findServicePushById(id);
        enableEdit = true;

        Iterable<ServiceProvider> listService = serviceProviderRepo.findAll();
        List<String> listServiceName = new ArrayList<String>();

        HashService.clear();
        for (ServiceProvider serv : listService) {
            listServiceName.add(serv.getServiceName());
            HashService.put(serv.getServiceName(), serv);
        }
        model.addAttribute("listService", listServiceName);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("srvpush", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ServicePush entity = new ServicePush();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = servicePushRepo.findServicePushById(id);
        }

        Iterable<ServiceProvider> listService = serviceProviderRepo.findAll();
        List<String> listServiceName = new ArrayList<String>();

        HashService.clear();
        for (ServiceProvider serv : listService) {
            listServiceName.add(serv.getServiceName());
            HashService.put(serv.getServiceName(), serv);
        }
        model.addAttribute("listService", listServiceName);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("srvpush", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("srvpush") ServicePush entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "servicepushs/form";
        if (errors.hasErrors()) {
            is_error = true;
        }

        String serv_name = entity.getServiceName();
        ServiceProvider serv = null;

        if (!StringUtils.isBlank(serv_name) && !serv_name.equals("NONE")) {
            serv = HashService.get(serv_name);
            entity.setServiceName(serv.getServiceName());
        }

        servicePushRepo.save(entity);
        status.setComplete();
        result = "redirect:/servicepushs/list";

        if (is_error) {
            Iterable<ServiceProvider> listService = serviceProviderRepo.findAll();
            List<String> listServiceName = new ArrayList<String>();
            HashService.clear();
            for (ServiceProvider srv : listService) {
                listServiceName.add(serv.getServiceName());
                HashService.put(srv.getServiceName(), srv);
            }
            model.addAttribute("listService", listServiceName);
        }
        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        ServicePush entity = servicePushRepo.findServicePushById(id);;
        enableEdit = true;

        Iterable<ServiceProvider> listService = serviceProviderRepo.findAll();
        List<String> listServiceName = new ArrayList<String>();

        HashService.clear();
        for (ServiceProvider serv : listService) {
            listServiceName.add(serv.getServiceName());
            HashService.put(serv.getServiceName(), serv);
        }
        model.addAttribute("listService", listServiceName);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("srvpush", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("srvpush") ServicePush entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            servicePushRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getServiceName())
                    .addObject("entityName", "products")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/servicepushs/list");
        }
        status.setComplete();
        return "redirect:/servicepushs/list";
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

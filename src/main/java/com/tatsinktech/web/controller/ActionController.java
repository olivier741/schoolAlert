/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.register.Action;
import com.tatsinktech.web.model.register.Product;
import com.tatsinktech.web.repository.ActionRepository;
import com.tatsinktech.web.repository.ProductRepository;
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

/**
 *
 * @author olivier
 */
@Controller
@RequestMapping("actions")
public class ActionController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, Product> HashProduct = new HashMap<String, Product>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private ActionRepository actionRepo;

    @Autowired
    private ProductRepository productRepo;

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
            return new ModelMap().addAttribute("actions", actionRepo.findByActionNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("actions", actionRepo.findAll(pageable));
        }
    }
    
       @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Action entity = actionRepo.findActionById(id);
        enableEdit = true;

        Iterable<Product> listProduct = productRepo.findAll();

        HashProduct.clear();

        for (Product prod : listProduct) {
            HashProduct.put(prod.getProductCode(), prod);
        }

        model.addAttribute("listProduct", listProduct);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("act", entity);
    }


    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Action entity = new Action();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = actionRepo.findActionById(id);
        }

        Iterable<Product> listProduct = productRepo.findAll();

        HashProduct.clear();
        for (Product prod : listProduct) {
            HashProduct.put(prod.getProductCode(), prod);
        }

        model.addAttribute("listProduct", listProduct);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("act", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("act") Action entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "actions/form";

        if (errors.hasErrors()) {
            is_error = true;
        }

        String prod_name = entity.getProduct().getProductCode();

        Product prod = null;

        if (!StringUtils.isBlank(prod_name) && !prod_name.equals("NONE")) {
            prod = HashProduct.get(prod_name);
        }

        entity.setProduct(prod);

        actionRepo.save(entity);
        status.setComplete();
        result = "redirect:/actions/list";

        if (is_error) {
            Iterable<Product> listProduct = productRepo.findAll();

            HashProduct.clear();

            for (Product prod1 : listProduct) {
                HashProduct.put(prod1.getProductCode(), prod1);
            }

            model.addAttribute("listProduct", listProduct);
        }

        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Action entity = actionRepo.findActionById(id);
        enableEdit = true;

        Iterable<Product> listProduct = productRepo.findAll();

        HashProduct.clear();

        for (Product prod : listProduct) {
            HashProduct.put(prod.getProductCode(), prod);
        }

        model.addAttribute("listProduct", listProduct);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("act", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("act") Action entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            actionRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getActionName())
                    .addObject("entityName", "actions")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/actions/list");
        }
        status.setComplete();
        return "redirect:/actions/list";
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

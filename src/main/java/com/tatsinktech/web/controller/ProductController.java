/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.register.Product;
import com.tatsinktech.web.model.register.Promotion;
import com.tatsinktech.web.model.register.ServiceProvider;
import com.tatsinktech.web.repository.ProductRepository;
import com.tatsinktech.web.repository.PromotionRepository;
import com.tatsinktech.web.repository.ServiceProviderRepository;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@RequestMapping("products")
public class ProductController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, Promotion> HashPomotion = new HashMap<String, Promotion>();
    private HashMap<String, ServiceProvider> HashService = new HashMap<String, ServiceProvider>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private PromotionRepository promoRepo;

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
        if (value != null) {
            model.addAttribute("key", value);
            return new ModelMap().addAttribute("products", productRepo.findByProductCodeContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("products", productRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Product entity = productRepo.findProductById(id);
        enableEdit = true;

        Iterable<Promotion> listPromotion = promoRepo.findAll();
        Iterable<ServiceProvider> listService = serviceRepo.findAll();

        HashPomotion.clear();
        for (Promotion promo : listPromotion) {
            HashPomotion.put(promo.getPromotionName(), promo);
        }

        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getServiceName(), serv);
        }

        model.addAttribute("listPromotion", listPromotion);
        model.addAttribute("listService", listService);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("product", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Product entity = new Product();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = productRepo.findProductById(id);
        }

        Iterable<Promotion> listPromotion = promoRepo.findAll();
        Iterable<ServiceProvider> listService = serviceRepo.findAll();

        HashPomotion.clear();
        for (Promotion promo : listPromotion) {
            HashPomotion.put(promo.getPromotionName(), promo);
        }

        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getServiceName(), serv);
        }

        model.addAttribute("listPromotion", listPromotion);
        model.addAttribute("listService", listService);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("product", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("product") Product entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "products/form";
        if (errors.hasErrors()) {

            is_error = true;

        }

        if (Optional.ofNullable(entity.getStartTime()).isPresent() && Optional.ofNullable(entity.getEndTime()).isPresent()) {

            if ( !(entity.getIsFrameValidity()!=null && entity.getIsFrameValidity()) && registerValidator(entity.getValidity()) && registerValidator(entity.getPendingDuration())) {

                String pro_name = entity.getPromotion().getPromotionName();
                String serv_name = entity.getService().getServiceName();

                Promotion pormo = null;
                ServiceProvider serv = null;

                if (!StringUtils.isBlank(pro_name) && !pro_name.equals("NONE")) {
                    pormo = HashPomotion.get(pro_name);
                }

                if (!StringUtils.isBlank(serv_name) && !serv_name.equals("NONE")) {
                    serv = HashService.get(serv_name);
                }

                entity.setPromotion(pormo);
                entity.setService(serv);

                productRepo.save(entity);
                status.setComplete();
                result = "redirect:/products/list";

            } else if (entity.getIsFrameValidity()!=null && entity.getIsFrameValidity()) {
                String frame_val = entity.getFrameTimeValidity();
                if (checkFrameTime(frame_val)) {

                    String pro_name = entity.getPromotion().getPromotionName();
                    String serv_name = entity.getService().getServiceName();

                    Promotion pormo = null;
                    ServiceProvider serv = null;

                    if (!StringUtils.isBlank(pro_name) && !pro_name.equals("NONE")) {
                        pormo = HashPomotion.get(pro_name);
                    }

                    if (!StringUtils.isBlank(serv_name) && !serv_name.equals("NONE")) {
                        serv = HashService.get(serv_name);
                    }

                    entity.setPromotion(pormo);
                    entity.setService(serv);

                    productRepo.save(entity);
                    status.setComplete();
                    result = "redirect:/products/list";

                } else {
                    is_error = true;
                }
            } else {
                is_error = true;
            }
        } else {
            is_error = true;
        }

        if (is_error) {
            Iterable<Promotion> listPromotion = promoRepo.findAll();
            Iterable<ServiceProvider> listService = serviceRepo.findAll();

            HashPomotion.clear();
            for (Promotion promo : listPromotion) {
                HashPomotion.put(promo.getPromotionName(), promo);
            }

            HashService.clear();
            for (ServiceProvider serv : listService) {
                HashService.put(serv.getServiceName(), serv);
            }

            model.addAttribute("listPromotion", listPromotion);
            model.addAttribute("listService", listService);
        }

        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Product entity = productRepo.findProductById(id);
        enableEdit = true;

        Iterable<Promotion> listPromotion = promoRepo.findAll();
        Iterable<ServiceProvider> listService = serviceRepo.findAll();

        HashPomotion.clear();
        for (Promotion promo : listPromotion) {
            HashPomotion.put(promo.getPromotionName(), promo);
        }

        HashService.clear();
        for (ServiceProvider serv : listService) {
            HashService.put(serv.getServiceName(), serv);
        }

        model.addAttribute("listPromotion", listPromotion);
        model.addAttribute("listService", listService);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("product", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("product") Product entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            productRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getProductCode())
                    .addObject("entityName", "products")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/products/list");
        }
        status.setComplete();
        return "redirect:/products/list";
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

    private boolean checkFrameTime(String frame_time) {
        if (!StringUtils.isBlank(frame_time)) {
            Pattern ptn = Pattern.compile("\\-");
            List<String> listTime = Arrays.asList(ptn.split(frame_time));
            if (time24HoursValidator(listTime.get(0)) && time24HoursValidator(listTime.get(1))) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean time24HoursValidator(String time_hour) {
        if (!StringUtils.isBlank(time_hour)) {
            String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
            Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
            Matcher matcher = pattern.matcher(time_hour);
            return matcher.matches();
        } else {
            return true;
        }

    }

    private boolean registerValidator(String periode) {
        if (!StringUtils.isBlank(periode)) {
            String regex_string = "^(D|H)([0-9])*";
            Pattern pattern = Pattern.compile(regex_string);
            Matcher matcher = pattern.matcher(periode);
            return matcher.matches();
        } else {
            return true;
        }

    }

}

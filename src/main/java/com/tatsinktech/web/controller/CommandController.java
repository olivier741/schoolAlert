/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.register.Action;
import com.tatsinktech.web.model.register.Command;
import com.tatsinktech.web.model.register.Notification_Conf;
import com.tatsinktech.web.model.register.Parameter;
import com.tatsinktech.web.model.register.Product;
import com.tatsinktech.web.repository.ActionRepository;
import com.tatsinktech.web.repository.CommandRepository;
import com.tatsinktech.web.repository.Notification_ConfRepository;
import com.tatsinktech.web.repository.ParameterRepository;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
@RequestMapping("commands")
public class CommandController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, Action> HashAction = new HashMap<String, Action>();
    private HashMap<String, Parameter> HashParameter = new HashMap<String, Parameter>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private CommandRepository commandRepo;

    @Autowired
    private ActionRepository actionRepo;

    @Autowired
    private ParameterRepository parameterRepo;

    @Autowired
    private Notification_ConfRepository notifyRepo;

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
            return new ModelMap().addAttribute("commands", commandRepo.findByCommandNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("commands", commandRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Command entity = commandRepo.findCommandById(id);
        enableEdit = true;

        Iterable<Action> listAction = actionRepo.findAll();
        Iterable<Parameter> listParameter = parameterRepo.findAll();

        HashAction.clear();
        for (Action act1 : listAction) {
            HashAction.put(act1.getActionName(), act1);
        }

        HashParameter.clear();
        for (Parameter param1 : listParameter) {
            HashParameter.put(param1.getParamName(), param1);
        }

        model.addAttribute("listAction", listAction);
        model.addAttribute("listParameter", listParameter);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("comd", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Command entity = new Command();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = commandRepo.findCommandById(id);
        }

        Iterable<Action> listAction = actionRepo.findAll();
        Iterable<Parameter> listParameter = parameterRepo.findAll();

        HashAction.clear();
        for (Action act : listAction) {
            HashAction.put(act.getActionName(), act);
        }

        HashParameter.clear();
        for (Parameter param1 : listParameter) {
            HashParameter.put(param1.getParamName(), param1);
        }

        model.addAttribute("listAction", listAction);
        model.addAttribute("listParameter", listParameter);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("comd", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("comd") Command entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "commands/form";
        if (errors.hasErrors()) {

            is_error = true;

        }

        String action_name = entity.getAction().getActionName();
        String param_name = entity.getParameter().getParamName();

        Action act = null;
        Parameter param0 = null;

        if (!StringUtils.isBlank(action_name) && !action_name.equals("NONE")) {
            act = HashAction.get(action_name);
        }

        if (!StringUtils.isBlank(param_name) && !param_name.equals("NONE")) {
            param0 = HashParameter.get(param_name);
        }

        entity.setAction(act);
        entity.setParameter(param0);

        Command cmd_save = commandRepo.save(entity);

        if (act != null) {
            Product product = act.getProduct();
            String productCode = product.getProductCode();
            List<String> listParamName = Arrays.asList("REG-PRODUCT-NOT-EXIST",
                    "CHECK-PRODUCT-NOT-EXIST",
                    "DEL-PRODUCT-NOT-EXIST",
                    "GUIDE-PRODUCT-NOT-EXIST",
                    "RECEIVER-ACTION-NOT-DEFINE",
                    "RECEIVER-NOT-ACTION-TYPE",
                    "RECEIVER-WRONG-SYNTAX",
                    "DEFAULT-CHANNEL",
                    "REG-PRODUCT-SUCCESS-" + productCode,
                    "REG-PRODUCT-SUCCESS-OVERIDE-" + productCode,
                    "REG-PRODUCT-NOT-OVERIDE-" + productCode,
                    "REG-PRODUCT-WRONG-TIME-" + productCode,
                    "REG-PRODUCT-NOT-AVAILABLE-" + productCode,
                    "REG-PRODUCT-EXPIRE-" + productCode,
                    "REG-PRODUCT-INVALID-DAY-" + productCode,
                    "REG-PRODUCT-WRONG-FRAMETIME-" + productCode,
                    "REG-PRODUCT-NOT-AVAILABLE-FRAMETIME-" + productCode,
                    "REG-PRODUCT-EXPIRE-FRAMETIME-" + productCode,
                    "REG-PRODUCT-RESTRICTION-EXIST-" + productCode,
                    "REG-PRODUCT-WRONG-CHARGE-" + productCode,
                    "REG-PRODUCT-WRONG-EXPIRRE_DATE-" + productCode,
                    "REG-PRODUCT-CUSTOMER-BLOCK-" + productCode,
                    "REG-PRODUCT-WRONG-API-CONNECTION-" + productCode,
                    "REG-PRODUCT-NOT-MONEY-" + productCode,
                    "REG-PRODUCT-PROMO-NOT-ALLOW-" + productCode,
                    "REG-PRODUCT-PROMO-WRONG-TIME-" + productCode,
                    "REG-PRODUCT-PROMO-ACCESS-DENY" + productCode,
                    "CHECK-PRODUCT-NOT-REGISTER-" + productCode,
                    "CHECK-PRODUCT-ACTIVE-ACTIVE-" + productCode,
                    "CHECK-PRODUCT-ACTIVE-CANCEL-" + productCode,
                    "CHECK-PRODUCT-ACTIVE-PENDING-" + productCode,
                    "CHECK-PRODUCT-CANCEL-CANCEL-" + productCode,
                    "CHECK-PRODUCT-WRONG-TIME-" + productCode,
                    "CHECK-PRODUCT-NOT-AVAILABLE-" + productCode,
                    "CHECK-PRODUCT-EXPIRE-" + productCode,
                    "DEL-PRODUCT-SUCCESS-" + productCode,
                    "DEL-PRODUCT-NOT-REGISTER-" + productCode,
                    "DEL-PRODUCT-ALREADY-CANCEL-" + productCode,
                    "GUIDE-PRODUCT-SUCCESS-" + productCode,
                    "GUIDE-PRODUCT-WRONG-TIME-" + productCode,
                    "GUIDE-PRODUCT-NOT-AVAILABLE-" + productCode,
                    "GUIDE-PRODUCT-EXPIRE-" + productCode);

            for (String paramName : listParamName) {
                if (notifyRepo.findNotification_ConfByNoficationName(paramName) == null) {
                    Notification_Conf notifConf = new Notification_Conf();
                    notifConf.setCommand(cmd_save);
                    notifConf.setNoficationName(paramName);
                    notifyRepo.save(notifConf);
                }

            }
        }

        status.setComplete();
        result = "redirect:/commands/list";

        if (is_error) {
            Iterable<Action> listAction = actionRepo.findAll();
            Iterable<Parameter> listParameter = parameterRepo.findAll();

            HashAction.clear();
            for (Action act1 : listAction) {
                HashAction.put(act1.getActionName(), act1);
            }

            HashParameter.clear();
            for (Parameter param1 : listParameter) {
                HashParameter.put(param1.getParamName(), param1);
            }

            model.addAttribute("listAction", listAction);
            model.addAttribute("listParameter", listParameter);
        }

        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Command entity = commandRepo.findCommandById(id);
        enableEdit = true;

        Iterable<Action> listAction = actionRepo.findAll();
        Iterable<Parameter> listParameter = parameterRepo.findAll();

        HashAction.clear();
        for (Action act1 : listAction) {
            HashAction.put(act1.getActionName(), act1);
        }

        HashParameter.clear();
        for (Parameter param1 : listParameter) {
            HashParameter.put(param1.getParamName(), param1);
        }

        model.addAttribute("listAction", listAction);
        model.addAttribute("listParameter", listParameter);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("comd", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("comd") Command entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            notifyRepo.deleteNotifByCommandName(entity);
            commandRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getCommandName())
                    .addObject("entityName", "commands")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/commands/list");
        }
        status.setComplete();
        return "redirect:/commands/list";
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

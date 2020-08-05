/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.model.register.Command;
import com.tatsinktech.web.model.register.Notification_Conf;
import com.tatsinktech.web.repository.CommandRepository;
import com.tatsinktech.web.repository.Notification_ConfRepository;
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
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("notifications")
public class Notification_ConfController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private HashMap<String, Command> HashCommand = new HashMap<String, Command>();

    private boolean enableSave = true;
    private boolean enableEdit = true;

    @Autowired
    private Notification_ConfRepository notifyRepo;

    @Autowired
    private CommandRepository commandRepo;

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
            return new ModelMap().addAttribute("notifications", notifyRepo.findByNoficationNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("notifications", notifyRepo.findAll(pageable));
        }
    }
    
     @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Notification_Conf entity = notifyRepo.findNotification_ConfById(id);
        enableEdit = true;

        Iterable<Command> listCommand = commandRepo.findAll();

        HashCommand.clear();
        for (Command comd : listCommand) {
            HashCommand.put(comd.getCommandName(), comd);
        }

        model.addAttribute("listCommand", listCommand);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("notif", entity);
    }


    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Notification_Conf entity = new Notification_Conf();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = notifyRepo.findNotification_ConfById(id);
        }

        Iterable<Command> listCommand = commandRepo.findAll();

        HashCommand.clear();
        for (Command comd : listCommand) {
            HashCommand.put(comd.getCommandName(), comd);
        }

        model.addAttribute("listCommand", listCommand);
        model.addAttribute("enableSave", enableSave);

        return new ModelMap("notif", entity);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("notif") Notification_Conf entity,
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        boolean is_error = false;
        String result = "notifications/form";

        if (errors.hasErrors()) {
            is_error = true;
        }

        String command_name = entity.getCommand().getCommandName();

        Command comd = null;

        if (!StringUtils.isBlank(command_name) && !command_name.equals("NONE")) {
            comd = HashCommand.get(command_name);
        }

        entity.setCommand(comd);

        notifyRepo.save(entity);
        status.setComplete();
        result = "redirect:/notifications/list";

        if (is_error) {
            Iterable<Command> listCommand = commandRepo.findAll();

            HashCommand.clear();
            for (Command comd1 : listCommand) {
                HashCommand.put(comd1.getCommandName(), comd1);
            }

            model.addAttribute("listCommand", listCommand);
        }

        return result;
    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        Notification_Conf entity = notifyRepo.findNotification_ConfById(id);
        enableEdit = true;

        Iterable<Command> listCommand = commandRepo.findAll();

        HashCommand.clear();
        for (Command comd : listCommand) {
            HashCommand.put(comd.getCommandName(), comd);
        }

        model.addAttribute("listCommand", listCommand);

        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("notif", entity);
    }

    @PostMapping("/delete")
    public Object postDelete(@Valid @ModelAttribute("notif") Notification_Conf entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            notifyRepo.delete(entity);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", entity.getNoficationName())
                    .addObject("entityName", "notifications")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/notifications/list");
        }
        status.setComplete();
        return "redirect:/notifications/list";
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

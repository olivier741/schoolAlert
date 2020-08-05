/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.controller;

import com.tatsinktech.web.beans.TableRestrict;
import com.tatsinktech.web.model.register.Promotion;
import com.tatsinktech.web.model.register.PromoTable;
import com.tatsinktech.web.repository.PromotionRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import com.tatsinktech.web.repository.PromoTableRepository;
import com.tatsinktech.web.repository.ManageTableRepository;
import com.tatsinktech.web.util.CsvUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author olivier.tatsinkou
 */
@Controller
@RequestMapping("promotables")
public class PromoTableController {

    private final String UPLOAD_DIR = "./uploads/";

    private boolean enableSave = true;
    private boolean enableEdit = true;
    private List<String> selectedMsisdn = new ArrayList<String>();
    private List<String> listCOntentType = new ArrayList<String>();
    private HashMap<String, Promotion> HashPomotion = new HashMap<String, Promotion>();

    private String respMessage = "";

    @Autowired
    private PromotionRepository promoRepo;

    @Autowired
    private PromoTableRepository promotabRepo;

    @Autowired
    private ManageTableRepository mgtTableSrv;

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

    public List<String> getSelectedMsisdn() {
        return selectedMsisdn;
    }

    public void setSelectedMsisdn(List<String> selectedMsisdn) {
        this.selectedMsisdn = selectedMsisdn;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        listCOntentType.add("text/plain");
        listCOntentType.add("application/vnd.ms-excel");
        listCOntentType.add("text/csv");
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
            return new ModelMap().addAttribute("promotables", promotabRepo.findByTableNameContainingIgnoreCase(value, pageable));
        } else {
            return new ModelMap().addAttribute("promotables", promotabRepo.findAll(pageable));
        }
    }

    @GetMapping("/view")
    public ModelMap getView(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        selectedMsisdn.clear();
        
        PromoTable entity = promotabRepo.findPromoTableById(id);
        enableEdit = true;
        List<String> listMsisdn = mgtTableSrv.getTable(entity.getTableName());

        model.addAttribute("listMsisdn", listMsisdn);
        model.addAttribute("selectedMsisdn", selectedMsisdn);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("promotab", entity);
    }

    @GetMapping("/form")
    public ModelMap getForm(@RequestParam(value = "id", required = false) Long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        TableRestrict tableRest = new TableRestrict();
        PromoTable entity = new PromoTable();
        selectedMsisdn.clear();
        enableSave = true;
        if (id != null) {
            enableSave = false;
            entity = promotabRepo.findPromoTableById(id);

            List<String> listMsisdn = mgtTableSrv.getTable(entity.getTableName());
            model.addAttribute("listMsisdn", listMsisdn);

        }else{
            int val = (int)(Math.random() * 300) + 1;
            String tableName ="restrict_table"+val;
            entity.setTableName(tableName);
        }
        tableRest.setPromotable(entity);
        tableRest.setSelectedMsisdn(selectedMsisdn);
        model.addAttribute("enableSave", enableSave);
        model.addAttribute("selectedMsisdn", selectedMsisdn);

        return new ModelMap("promotab", tableRest);
    }

    @PostMapping("/form")
    public String postForm(@Valid @ModelAttribute("promotab") TableRestrict entity, @RequestParam("uploadfile") MultipartFile file, 
            BindingResult errors, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);

        if (errors.hasErrors()) {
            return "/promotables/form";
        }

        String tableName = entity.getPromotable().getTableName();
        selectedMsisdn = entity.getSelectedMsisdn();
        try {
            if (listCOntentType.contains(file.getContentType())) {
                List<String> listMsisdn = CsvUtils.parseCsvFile(file.getInputStream());
                if (listMsisdn != null && !listMsisdn.isEmpty()) {
                    mgtTableSrv.createTable(tableName);

                    for (String msisdn : listMsisdn) {
                        mgtTableSrv.insertTable(tableName, msisdn);
                    }
                }
            }

            if (selectedMsisdn != null && !selectedMsisdn.isEmpty()) {
                for (String msisdn : selectedMsisdn) {
                    mgtTableSrv.deleteTable(tableName, msisdn);
                }
            }

            model.addAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
        }

        promotabRepo.save( entity.getPromotable());
        status.setComplete();
        return "redirect:/promotables/list";

    }

    @GetMapping("/delete")
    public ModelMap getDelete(@RequestParam(value = "id", required = true) long id, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        PromoTable entity = promotabRepo.findPromoTableById(id);
        enableSave = false;

        List<String> listMsisdn = mgtTableSrv.getTable(entity.getTableName());

        model.addAttribute("listMsisdn", listMsisdn);
        model.addAttribute("selectedMsisdn", selectedMsisdn);
        model.addAttribute("enableEdit", enableEdit);
        return new ModelMap("promotab", entity);

    }

    @PostMapping("/delete")
    public String postDelete(@Valid @ModelAttribute("promotab") PromoTable entity, SessionStatus status, Model model, @NotNull Authentication auth) {
        loadMode(model, auth);
        try {
            mgtTableSrv.dropTable(entity.getTableName());
            promotabRepo.delete(entity);
        } catch (Exception exception) {
            respMessage = "Cannot Delete this Service";
            model.addAttribute("respMessage", respMessage);
            status.setComplete();

            return "redirect:/promotables/delete";
        }
        status.setComplete();
        return "redirect:/promotables/list";
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

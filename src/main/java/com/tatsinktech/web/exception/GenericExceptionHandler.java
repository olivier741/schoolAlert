//package com.tatsinktech.web.exception;
//
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.logging.Logger;
//
///**
// * Created by pavan on 9/12/18.
// */
//@ControllerAdvice
//public class GenericExceptionHandler {
//
//    private Logger logger = Logger.getLogger(this.getClass().getName());
//
//    @ExceptionHandler(BindException.class)
//    public String handleInvalidFormatExeption(BindException ex, RedirectAttributes redirectAttributes){
//        redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format");
//        return "redirect:/";
//    }
//
//}

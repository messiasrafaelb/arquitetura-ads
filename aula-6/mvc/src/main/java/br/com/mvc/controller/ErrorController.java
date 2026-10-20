package br.com.mvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateEngineException;
@ControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("mensagem", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(TemplateEngineException.class)
    public String handleTemplateInputException(TemplateEngineException ex, Model model) {
        model.addAttribute("mensagem", ex.getMessage());
        return "error";
    }
}

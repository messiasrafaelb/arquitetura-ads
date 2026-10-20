package br.com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.model.entity.Product;
import br.com.mvc.model.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @GetMapping()
    public String read(Model model) {
        model.addAttribute("products", service.read());
        model.addAttribute("product", new Product());
        return "products";
    }

    @PostMapping()
    public String create(@ModelAttribute Product product) {
        service.create(product);
        return "redirect:/products";
    }

}

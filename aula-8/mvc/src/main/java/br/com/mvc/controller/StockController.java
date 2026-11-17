package br.com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mvc.model.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final ProductService productService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products", productService.read());

        return "stock";
    }

    @PostMapping("/entry")
    public String entry(@RequestParam Long productId, @RequestParam Integer quantity) {
        productService.entryStock(productId, quantity);

        return "redirect:/stock";
    }

    @PostMapping("/exit")
    public String exit(@RequestParam Long productId, @RequestParam Integer quantity) {
        productService.exitStock(productId, quantity);

        return "redirect:/stock";
    }
}

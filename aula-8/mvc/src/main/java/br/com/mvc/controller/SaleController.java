package br.com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mvc.model.entity.Sale;
import br.com.mvc.model.service.SaleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public String read(Model model) {
        model.addAttribute("sales", saleService.read());

        model.addAttribute("sale", new Sale());

        return "sales";
    }

    @PostMapping
    public String create(@ModelAttribute Sale sale) {
        saleService.create(sale);

        return "redirect:/sales";
    }
}

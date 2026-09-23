package br.uel.invskins.controller;

import br.uel.invskins.service.SkinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SkinService skinService;

    public HomeController(SkinService skinService) {
        this.skinService = skinService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("skins", skinService.listar());
        return "index";
    }
}
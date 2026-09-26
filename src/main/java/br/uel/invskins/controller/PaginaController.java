package br.uel.invskins.controller;

import br.uel.invskins.service.InventarioService;
import br.uel.invskins.service.SkinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaginaController {

    private final InventarioService inventarioService;
    private final SkinService skinService;

    public PaginaController(
            InventarioService inventarioService,
            SkinService skinService) {
        this.inventarioService = inventarioService;
        this.skinService = skinService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/inventarios")
    public String listarInventarios(Model model) {
        model.addAttribute("inventarios", inventarioService.listar());
        return "inventarios";
    }

    @GetMapping("/inventarios/{id}")
    public String detalheInventario(@PathVariable Long id, Model model) {
        return inventarioService.buscarPorId(id)
                .map(inventario -> {
                    model.addAttribute("inventario", inventario);
                    return "inventario-detalhe";
                })
                .orElse("redirect:/inventarios");
    }

    @GetMapping("/skins")
    public String catalogo(
            @RequestParam(required = false, defaultValue = "") String nome,
            Model model) {

        model.addAttribute("skins", skinService.buscarSkins(nome));
        model.addAttribute("termo", nome);
        model.addAttribute("inventarios", inventarioService.listar());

        return "catalogo";
    }
}
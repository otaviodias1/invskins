package br.uel.invskins.controller;

import br.uel.invskins.service.InventarioService;
import br.uel.invskins.service.SkinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/inventarios")
    public String listarInventarios(Model model) {

        model.addAttribute(
                "inventarios",
                inventarioService.listar()
        );

        return "inventarios";
    }

    @GetMapping("/inventarios/{id}")
    public String detalheInventario(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String nome,
            Model model) {

        return inventarioService.buscarPorId(id)
                .map(inventario -> {

                    model.addAttribute("inventario", inventario);

                    // Busca das skins feita pelo Spring
                    model.addAttribute(
                            "skinsBusca",
                            skinService.buscarSkins(nome)
                    );

                    model.addAttribute("termoBusca", nome);

                    return "inventario-detalhe";
                })
                .orElse("redirect:/inventarios");
    }

    @PostMapping("/inventarios/{id}/itens")
    public String adicionarItem(
            @PathVariable Long id,
            @RequestParam String skinExternalId,
            @RequestParam(defaultValue = "1") Integer quantidade) {

        inventarioService.adicionarItem(
                id,
                skinExternalId,
                quantidade
        );

        return "redirect:/inventarios/" + id;
    }

    @GetMapping("/skins")
    public String catalogo(
            @RequestParam(defaultValue = "") String nome,
            Model model) {

        model.addAttribute(
                "skins",
                skinService.buscarSkins(nome)
        );

        model.addAttribute("termo", nome);

        model.addAttribute(
                "inventarios",
                inventarioService.listar()
        );

        return "catalogo";
    }
    @PostMapping("/inventarios/{id}/itens/{itemId}/remover")
    public String removerItem(
            @PathVariable Long id,
            @PathVariable Long itemId) {

        inventarioService.removerItem(id, itemId);

        return "redirect:/inventarios/" + id;
    }

}
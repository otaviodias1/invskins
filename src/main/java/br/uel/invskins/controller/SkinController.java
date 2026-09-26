package br.uel.invskins.controller;

import br.uel.invskins.model.Skin;
import br.uel.invskins.service.SkinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skins")
public class SkinController {

    private final SkinService skinService;

    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Skin>> buscar(@RequestParam String nome) {
        return ResponseEntity.ok(skinService.buscarSkins(nome));
    }
}

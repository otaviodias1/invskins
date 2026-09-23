package br.uel.invskins.service;

import br.uel.invskins.dto.SkinSearchResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkinService {

    private final SkinApiService skinApiService;

    public SkinService(SkinApiService skinApiService) {
        this.skinApiService = skinApiService;
    }

    public List<SkinSearchResultDTO> buscarSkins(String nome) {
        String termo = nome.toLowerCase();
        return skinApiService.carregarTodasAsSkins().stream()
                .filter(skin -> skin.nome().toLowerCase().contains(termo))
                .toList();
    }
}
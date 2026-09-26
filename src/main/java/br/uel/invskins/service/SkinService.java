package br.uel.invskins.service;

import br.uel.invskins.model.Skin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkinService {

    private final SkinApiService skinApiService;

    public SkinService(SkinApiService skinApiService) {
        this.skinApiService = skinApiService;
    }

    public List<Skin> buscarSkins(String nome) {
        String termo = nome == null ? "" : nome.toLowerCase();
        return skinApiService.carregarTodasAsSkins().stream()
                .filter(skin -> skin.getNome().toLowerCase().contains(termo))
                .toList();
    }

    public Optional<Skin> buscarPorExternalId(String externalId) {
        return skinApiService.carregarTodasAsSkins().stream()
                .filter(skin -> skin.getExternalId().equals(externalId))
                .findFirst();
    }
}

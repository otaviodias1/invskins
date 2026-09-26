package br.uel.invskins.service;

import br.uel.invskins.model.Skin;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class SkinApiService {

    private static final String ARQUIVO_CATALOGO = "skins-mock.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Skins vindas do catálogo aqui ainda não existem no banco (id = null)
    public List<Skin> carregarTodasAsSkins() {
        try (InputStream fluxo = new ClassPathResource(ARQUIVO_CATALOGO).getInputStream()) {
            Skin[] skins = objectMapper.readValue(fluxo, Skin[].class);
            return Arrays.asList(skins);
        } catch (Exception e) {
            System.out.println("Erro ao carregar o catalogo de skins: " + e.getMessage());
            return List.of();
        }
    }
}

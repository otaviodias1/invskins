package br.uel.invskins.dto;

import java.time.LocalDateTime;

public record InventarioResponseDTO(
        Long id,
        String nome,
        String descricao,
        LocalDateTime dataCriacao
) {}
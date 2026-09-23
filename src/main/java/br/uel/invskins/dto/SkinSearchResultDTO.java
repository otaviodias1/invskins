package br.uel.invskins.dto;

public record SkinSearchResultDTO(
        String externalId,
        String nome,
        String arma,
        String raridade,
        String imagem,
        Double preco
) {}

//aaamanter o front independente e so modificar skinapiservice caso algo mude, o front so recebe e m ostra n precisa tratar os dados
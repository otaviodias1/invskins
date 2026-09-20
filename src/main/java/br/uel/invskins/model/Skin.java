package br.uel.invskins.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "skins")
public class Skin {

    // Getters e Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", length = 255)
    private String externalId;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 100)
    private String arma;

    @Column(length = 50)
    private String raridade;

    @Column(length = 500)
    private String imagem;

    private Double preco;

    // Construtor vazio (obrigatório para JPA)
    public Skin() {
    }

    public Skin(String externalId, String nome, String arma, String raridade, String imagem, Double preco) {
        this.externalId = externalId;
        this.nome = nome;
        this.arma = arma;
        this.raridade = raridade;
        this.imagem = imagem;
        this.preco = preco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
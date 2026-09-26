package br.uel.invskins.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemInventario> itens = new ArrayList<>();

    public Inventario() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Inventario(String nome, String descricao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void adicionarItem(ItemInventario item) {
        itens.add(item);
        item.setInventario(this);
    }

    public void removerItem(ItemInventario item) {
        itens.remove(item);
        item.setInventario(null);
    }

    // Calculado a partir dos itens — não é persistido, só aparece no JSON/Thymeleaf
    public double getValorTotal() {
        return itens.stream()
                .mapToDouble(item -> {
                    Double preco = item.getSkin() != null ? item.getSkin().getPreco() : null;
                    return (preco != null ? preco : 0) * item.getQuantidade();
                })
                .sum();
    }
}

package br.uel.invskins.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false, length = 100)
    private String nome;

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
}
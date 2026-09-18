package br.uel.invskins.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "itens_inventario")
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "skin_id", nullable = false)
    private Skin skin;

    @Column(nullable = false)
    private Integer quantidade;

    public ItemInventario() {
        this.quantidade = 1;
    }

    public ItemInventario(Inventario inventario, Skin skin, Integer quantidade) {
        this.inventario = inventario;
        this.skin = skin;
        this.quantidade = quantidade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
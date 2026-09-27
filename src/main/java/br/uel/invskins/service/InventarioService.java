package br.uel.invskins.service;

import br.uel.invskins.model.Inventario;
import br.uel.invskins.model.ItemInventario;
import br.uel.invskins.model.Skin;
import br.uel.invskins.repository.InventarioRepository;
import br.uel.invskins.repository.ItemInventarioRepository;
import br.uel.invskins.repository.SkinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ItemInventarioRepository itemInventarioRepository;
    private final SkinRepository skinRepository;
    private final SkinService skinService;

    public InventarioService(InventarioRepository inventarioRepository,
                              ItemInventarioRepository itemInventarioRepository,
                              SkinRepository skinRepository,
                              SkinService skinService) {
        this.inventarioRepository = inventarioRepository;
        this.itemInventarioRepository = itemInventarioRepository;
        this.skinRepository = skinRepository;
        this.skinService = skinService;
    }

    @Transactional(readOnly = true)
    public List<Inventario> listar() {
        return inventarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Inventario> buscarPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Inventario criar(Inventario dadosRecebidos) {
        // Monta um Inventario novo a partir só do nome/descrição — ignora id/itens
        // que o cliente eventualmente tenha mandado no corpo da requisição.
        Inventario novo = new Inventario(dadosRecebidos.getNome(), dadosRecebidos.getDescricao());
        return inventarioRepository.save(novo);
    }

    public Optional<Inventario> atualizar(Long id, Inventario dadosRecebidos) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setNome(dadosRecebidos.getNome());
            inventario.setDescricao(dadosRecebidos.getDescricao());
            return inventarioRepository.save(inventario);
        });
    }

    public boolean excluir(Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<Inventario> adicionarItem(Long inventarioId, String skinExternalId, Integer quantidade) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findById(inventarioId);
        if (inventarioOpt.isEmpty()) {
            return Optional.empty();
        }

        Skin skin = obterOuCriarSkin(skinExternalId);
        int qtd = quantidade != null ? quantidade : 1;

        Optional<ItemInventario> existente =
                itemInventarioRepository.findByInventarioIdAndSkinId(inventarioId, skin.getId());

        if (existente.isPresent()) {
            ItemInventario item = existente.get();
            item.setQuantidade(item.getQuantidade() + qtd);
            itemInventarioRepository.save(item);
        } else {
            itemInventarioRepository.save(new ItemInventario(inventarioOpt.get(), skin, qtd));
        }

        return inventarioRepository.findById(inventarioId);
    }

    @Transactional
    public Optional<Inventario> atualizarQuantidade(Long inventarioId, Long itemId, Integer quantidade) {
        return itemInventarioRepository.findByIdAndInventarioId(itemId, inventarioId)
                .map(item -> {
                    item.setQuantidade(quantidade);
                    itemInventarioRepository.save(item);
                    return inventarioRepository.findById(inventarioId).orElseThrow();
                });
    }

    @Transactional
    public boolean removerItem(Long inventarioId, Long itemId) {
        return itemInventarioRepository.findByIdAndInventarioId(itemId, inventarioId)
                .map(item -> {
                    itemInventarioRepository.delete(item);
                    return true;
                })
                .orElse(false);
    }

    private Skin obterOuCriarSkin(String externalId) {

        Skin doCatalogo = skinService.buscarPorExternalId(externalId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Skin não encontrada no catálogo: " + externalId));

        Optional<Skin> existente =
                skinRepository.findByExternalId(externalId);

        if (existente.isPresent()) {

            Skin skin = existente.get();

            skin.setNome(doCatalogo.getNome());
            skin.setArma(doCatalogo.getArma());
            skin.setRaridade(doCatalogo.getRaridade());
            skin.setImagem(doCatalogo.getImagem());
            skin.setPreco(doCatalogo.getPreco());

            return skinRepository.save(skin);
        }

        Skin nova = new Skin(
                doCatalogo.getExternalId(),
                doCatalogo.getNome(),
                doCatalogo.getArma(),
                doCatalogo.getRaridade(),
                doCatalogo.getImagem(),
                doCatalogo.getPreco()
        );

        return skinRepository.save(nova);
    }
}

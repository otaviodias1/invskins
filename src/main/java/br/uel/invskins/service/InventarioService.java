package br.uel.invskins.service;

import br.uel.invskins.dto.InventarioRequestDTO;
import br.uel.invskins.dto.InventarioResponseDTO;
import br.uel.invskins.model.Inventario;
import br.uel.invskins.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public List<InventarioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::paraResponse)
                .toList();
    }

    public Optional<InventarioResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::paraResponse);
    }

    public InventarioResponseDTO criar(InventarioRequestDTO dto) {
        Inventario inventario = new Inventario(dto.nome(), dto.descricao());
        return paraResponse(repository.save(inventario));
    }

    public Optional<InventarioResponseDTO> atualizar(Long id, InventarioRequestDTO dto) {
        return repository.findById(id).map(inventario -> {
            inventario.setNome(dto.nome());
            inventario.setDescricao(dto.descricao());
            return paraResponse(repository.save(inventario));
        });
    }

    public boolean excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private InventarioResponseDTO paraResponse(Inventario inventario) {
        return new InventarioResponseDTO(
                inventario.getId(),
                inventario.getNome(),
                inventario.getDescricao(),
                inventario.getDataCriacao()
        );
    }
}
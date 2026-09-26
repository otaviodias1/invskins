package br.uel.invskins.repository;

import br.uel.invskins.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {

    Optional<ItemInventario> findByInventarioIdAndSkinId(Long inventarioId, Long skinId);

    Optional<ItemInventario> findByIdAndInventarioId(Long id, Long inventarioId);
}

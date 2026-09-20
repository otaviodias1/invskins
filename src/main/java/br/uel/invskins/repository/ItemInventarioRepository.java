package br.uel.invskins.repository;

import br.uel.invskins.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
}
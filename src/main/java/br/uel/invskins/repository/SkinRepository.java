package br.uel.invskins.repository;

import br.uel.invskins.model.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {

    Optional<Skin> findByExternalId(String externalId);
}

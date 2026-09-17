package br.uel.invskins.service;

import br.uel.invskins.model.Skin;
import br.uel.invskins.repository.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkinService {

    @Autowired
    private SkinRepository skinRepository;

    public List<Skin> listar() {
        return skinRepository.findAll();
    }

    public Skin buscarPorId(Long id) {
        Optional<Skin> skin = skinRepository.findById(id);
        return skin.orElse(null);
    }


    public Skin salvar(Skin skin) {
        return skinRepository.save(skin);
    }

    public Skin atualizar(Long id, Skin skinAtualizada) {
        if (skinRepository.existsById(id)) {
            skinAtualizada.setId(id);
            return skinRepository.save(skinAtualizada);
        }
        return null;
    }


    public boolean deletar(Long id) {
        if (skinRepository.existsById(id)) {
            skinRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
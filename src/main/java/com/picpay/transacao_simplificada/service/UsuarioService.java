package com.picpay.transacao_simplificada.service;

import com.picpay.transacao_simplificada.exceptions.UserNotFound;
import com.picpay.transacao_simplificada.infrastructure.entity.Usuario;
import com.picpay.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private final UsuarioRepository repository;

    public Usuario buscarUsuario(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFound("Usuario não encontrado"));
    }
}

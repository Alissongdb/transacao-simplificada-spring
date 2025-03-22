package com.picpay.transacao_simplificada.service;

import com.picpay.transacao_simplificada.infrastructure.entity.Carteira;
import com.picpay.transacao_simplificada.infrastructure.repository.CarteiraRepository;
import com.picpay.transacao_simplificada.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository repository;

    public void salvar(Carteira carteira) {
        repository.save(carteira);
    }
}

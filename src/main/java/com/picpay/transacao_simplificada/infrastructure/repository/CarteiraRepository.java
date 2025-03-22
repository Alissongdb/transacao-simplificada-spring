package com.picpay.transacao_simplificada.infrastructure.repository;

import com.picpay.transacao_simplificada.infrastructure.entity.Carteira;
import com.picpay.transacao_simplificada.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}

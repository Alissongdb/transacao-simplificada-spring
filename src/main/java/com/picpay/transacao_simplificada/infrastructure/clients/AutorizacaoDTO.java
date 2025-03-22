package com.picpay.transacao_simplificada.infrastructure.clients;

import javax.xml.crypto.Data;

public record AutorizacaoDTO(String status, DataDTO data) {
}

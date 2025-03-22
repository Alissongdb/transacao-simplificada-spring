package com.picpay.transacao_simplificada.dtos;

import java.math.BigDecimal;

public record TransacaoDTO(BigDecimal valor, Long payer, Long payee) {
}

package com.picpay.transacao_simplificada.controller;

import com.picpay.transacao_simplificada.dtos.TransacaoDTO;
import com.picpay.transacao_simplificada.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        transacaoService.transferirValores(transacaoDTO);
        return ResponseEntity.accepted().build();
    }
}

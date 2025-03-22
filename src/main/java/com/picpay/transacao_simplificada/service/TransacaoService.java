package com.picpay.transacao_simplificada.service;

import com.picpay.transacao_simplificada.dtos.TransacaoDTO;
import com.picpay.transacao_simplificada.exceptions.BadRequestException;
import com.picpay.transacao_simplificada.infrastructure.entity.Carteira;
import com.picpay.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.picpay.transacao_simplificada.infrastructure.entity.Transacoes;
import com.picpay.transacao_simplificada.infrastructure.entity.Usuario;
import com.picpay.transacao_simplificada.infrastructure.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Transactional
    public void transferirValores(TransacaoDTO  transacaoDTO) {
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validaPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.valor());
        validarTransferencia();

        pagador.getCarteira().setSaldo(pagador.getCarteira().getSaldo().subtract(transacaoDTO.valor()));
        atualizarSaldoCarteira(pagador.getCarteira());
        recebedor.getCarteira().setSaldo(recebedor.getCarteira().getSaldo().add(transacaoDTO.valor()));
        atualizarSaldoCarteira(recebedor.getCarteira());

        Transacoes transacoes = Transacoes.builder()
                .valor(transacaoDTO.valor())
                .pagador(pagador)
                .recebedor(recebedor)
                .build();

        transacaoRepository.save(transacoes);
        enviarNotificacao();

    }

    private void validaPagadorLojista(Usuario usuario) {
        try {
            if (usuario.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
                throw new IllegalArgumentException("Transacao não autorizada para este tipo de usuario");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarSaldoUsuario(Usuario usuario, BigDecimal valor) {
        try {
            if (usuario.getCarteira().getSaldo().compareTo(valor) < 0) {
                throw new IllegalArgumentException("Transacao não autorizada, Saldo insuficiente: " + usuario.getCarteira().getSaldo());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarTransferencia() {
        try {
            if (!autorizacaoService.validarTransferencia()) {
                throw new IllegalArgumentException("Transacao não autorizada pela API");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void atualizarSaldoCarteira(Carteira carteira) {
        carteiraService.salvar(carteira);
    }

    private void enviarNotificacao() {
        try {
            notificacaoService.enviarNotificacao();
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("Erro ao enviar notificacao");
        }
    }

}

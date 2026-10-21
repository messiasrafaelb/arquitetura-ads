package br.com.arquitetura.hexagonal.application;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;
import br.com.arquitetura.hexagonal.domain.ports.in.FaturaService;
import br.com.arquitetura.hexagonal.domain.ports.out.messaging.BoletoMessaging;
import br.com.arquitetura.hexagonal.domain.ports.out.persistence.FaturaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaturaServiceImpl implements FaturaService {

    private final FaturaRepository repository;
    private final BoletoMessaging boletoMessaging;

    @Override
    public UUID faturarPedido(Long pedidoId, BigDecimal valor) {
        var fatura = Fatura.criar(pedidoId, valor);
        var codigoBarras = boletoMessaging.emitirBoleto(fatura);

        fatura.inserirBoleto(codigoBarras);

        /*
         * TODO — Gerar boleto — Emitir nota — Disparar email com boleto e nota
         */

        repository.salvar(fatura);

        return fatura.getId();
    }
}

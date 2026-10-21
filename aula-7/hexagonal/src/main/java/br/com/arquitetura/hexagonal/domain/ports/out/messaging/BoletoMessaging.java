package br.com.arquitetura.hexagonal.domain.ports.out.messaging;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface BoletoMessaging {
    String emitirBoleto(Fatura fatura);
}

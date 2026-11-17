package br.com.arquitetura.hexagonal.infra.adapters.out.messaging;

import org.springframework.stereotype.Component;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;
import br.com.arquitetura.hexagonal.domain.ports.out.messaging.BoletoMessaging;

@Component
public class BoletoMessagingImp implements BoletoMessaging {

    @Override
    public String emitirBoleto(Fatura fatura) {
        long valorCentavos = fatura.getValor() != null
                ? fatura.getValor().movePointRight(2).longValue()
                : 1000L;
        return String.format("34191.79001 01043.510047 91020.150008 1 %010d", valorCentavos);
    }
}

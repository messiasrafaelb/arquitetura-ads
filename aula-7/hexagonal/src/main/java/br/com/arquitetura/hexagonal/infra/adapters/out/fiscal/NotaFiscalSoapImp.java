package br.com.arquitetura.hexagonal.infra.adapters.out.fiscal;

import org.springframework.stereotype.Component;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;
import br.com.arquitetura.hexagonal.domain.ports.out.fiscal.NotaFiscalSoap;

@Component
public class NotaFiscalSoapImp implements NotaFiscalSoap {

    @Override
    public String emitirNotaFiscal(Fatura fatura) {
        long pedidoNum = fatura.getPedidoId() != null ? fatura.getPedidoId() % 1_000_000_000L : 1L;
        return String.format("3526110000000000019155001%09d1123456789", pedidoNum);
    }
}

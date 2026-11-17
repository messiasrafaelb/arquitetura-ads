package br.com.arquitetura.hexagonal.domain.ports.out.fiscal;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface NotaFiscalSoap {
    String emitirNotaFiscal(Fatura fatura);
}

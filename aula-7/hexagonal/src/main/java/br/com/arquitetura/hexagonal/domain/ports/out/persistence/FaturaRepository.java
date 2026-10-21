package br.com.arquitetura.hexagonal.domain.ports.out.persistence;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface FaturaRepository {
    void salvar(Fatura fatura);
}

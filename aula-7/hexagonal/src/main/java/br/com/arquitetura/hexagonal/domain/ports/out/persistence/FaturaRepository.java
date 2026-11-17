package br.com.arquitetura.hexagonal.domain.ports.out.persistence;

import java.util.Optional;
import java.util.UUID;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface FaturaRepository {
    void salvar(Fatura fatura);
    Optional<Fatura> buscarPorId(UUID id);
}

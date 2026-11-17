package br.com.arquitetura.hexagonal.domain.ports.in;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface FaturaService {
    UUID faturarPedido(Long pedidoId, BigDecimal valor);
    Optional<Fatura> buscarPorId(UUID id);
}

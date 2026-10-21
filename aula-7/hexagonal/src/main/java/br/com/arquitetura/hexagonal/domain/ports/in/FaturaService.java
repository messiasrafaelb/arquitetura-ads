package br.com.arquitetura.hexagonal.domain.ports.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface FaturaService {
    UUID faturarPedido(Long pedidoId, BigDecimal valor);
}

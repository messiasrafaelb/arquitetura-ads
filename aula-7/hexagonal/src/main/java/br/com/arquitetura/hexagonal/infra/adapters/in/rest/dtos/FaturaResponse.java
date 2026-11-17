package br.com.arquitetura.hexagonal.infra.adapters.in.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public record FaturaResponse(
        UUID id,
        Long pedidoId,
        BigDecimal valor,
        LocalDateTime emitidoEm,
        String codigoBarras,
        String rps,
        String hashEmail
) {
    public static FaturaResponse deEntidade(Fatura fatura) {
        return new FaturaResponse(
                fatura.getId(),
                fatura.getPedidoId(),
                fatura.getValor(),
                fatura.getEmitidoEm(),
                fatura.getCodigoBarras(),
                fatura.getRps(),
                fatura.getHashEmail()
        );
    }
}

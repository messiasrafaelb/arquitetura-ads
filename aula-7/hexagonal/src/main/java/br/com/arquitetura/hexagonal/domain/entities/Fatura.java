package br.com.arquitetura.hexagonal.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Fatura {

    private UUID id;
    private String rps;
    private Long pedidoId;
    private BigDecimal valor;
    private String hashEmail;
    private String codigoBarras;
    private LocalDateTime emitidoEm;

    @Builder
    private Fatura(UUID id, String rps, Long pedidoId, BigDecimal valor, String hashEmail, String codigoBarras,
            LocalDateTime emitidoEm) {

        if (pedidoId == null || pedidoId <= 0) {
            throw new RuntimeException("A fatura precisa de um pedido!");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor da fatura deve ser maior que zero!");
        }

        this.id = id;
        this.rps = rps;
        this.valor = valor;
        this.pedidoId = pedidoId;
        this.hashEmail = hashEmail;
        this.emitidoEm = emitidoEm;
        this.codigoBarras = codigoBarras;
    }

    public static Fatura criar(Long pedidoId, BigDecimal valor) {
        return Fatura.builder().id(UUID.randomUUID()).pedidoId(pedidoId).valor(valor).build();
    }

    public void inserirBoleto(String codigoBarras) {
        /*
         * TODO — Regra de negócio do boleto
         */

        this.codigoBarras = codigoBarras;
    }
}

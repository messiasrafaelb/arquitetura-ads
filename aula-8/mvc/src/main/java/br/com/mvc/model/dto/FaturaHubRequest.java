package br.com.mvc.model.dto;

import java.math.BigDecimal;

public record FaturaHubRequest(Long pedidoId, BigDecimal valor) {
}

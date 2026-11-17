package br.com.arquitetura.hexagonal.domain.ports.out.email;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;

public interface EmailMessaging {
    String enviarEmail(Fatura fatura);
}

package br.com.arquitetura.hexagonal.infra.adapters.out.messaging;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;
import br.com.arquitetura.hexagonal.domain.ports.out.email.EmailMessaging;

@Component
public class EmailMessagingImp implements EmailMessaging {

    @Override
    public String enviarEmail(Fatura fatura) {
        try {
            String conteudo = "Fatura:" + fatura.getId() + ";Pedido:" + fatura.getPedidoId() + ";Valor:" + fatura.getValor();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(conteudo.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "HASH-" + System.currentTimeMillis();
        }
    }
}

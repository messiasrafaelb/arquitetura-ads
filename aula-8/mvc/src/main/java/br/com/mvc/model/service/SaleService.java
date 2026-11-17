package br.com.mvc.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.mvc.model.dto.FaturaHubRequest;
import br.com.mvc.model.dto.FaturaHubResponse;
import br.com.mvc.model.entity.Sale;
import br.com.mvc.model.repository.SaleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;

    @Value("${hub.faturamento.url:http://localhost:8080/fatura}")
    private String hubFaturamentoUrl;

    public List<Sale> read() {
        return repository.findAll();
    }

    public Sale create(Sale sale) {
        sale.setCreatedAt(LocalDateTime.now());

        sale.setStatus("PROCESSANDO");

        Sale saved = repository.save(sale);

        try {
            RestTemplate restTemplate = new RestTemplate();

            FaturaHubRequest request = new FaturaHubRequest(saved.getId(), saved.getAmount());

            FaturaHubResponse response = restTemplate.postForObject(hubFaturamentoUrl, request, FaturaHubResponse.class);

            if (response != null && response.faturaId() != null) {
                saved.setFaturaId(response.faturaId());

                saved.setStatus("FATURADO");
            } else {
                saved.setStatus("FATURAMENTO_PENDENTE");
            }
        } catch (Exception e) {
            saved.setStatus("ERRO_COMUNICACAO_HUB");
        }

        return repository.save(saved);
    }
}

package br.com.arquitetura.hexagonal.infra.adapters.in.rest.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arquitetura.hexagonal.domain.ports.in.FaturaService;
import br.com.arquitetura.hexagonal.infra.adapters.in.rest.dtos.FaturaPostRequest;
import br.com.arquitetura.hexagonal.infra.adapters.in.rest.dtos.FaturaPostResponse;
import br.com.arquitetura.hexagonal.infra.adapters.in.rest.dtos.FaturaResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fatura")
@RequiredArgsConstructor
public class FaturaController {

    private final FaturaService faturaService;

    @PostMapping
    public ResponseEntity<FaturaPostResponse> post(@RequestBody FaturaPostRequest request) {
        UUID faturaId = faturaService.faturarPedido(request.pedidoId(), request.valor());

        return ResponseEntity.status(201).body(new FaturaPostResponse(faturaId.toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturaResponse> getById(@PathVariable UUID id) {
        return faturaService.buscarPorId(id)
                .map(FaturaResponse::deEntidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

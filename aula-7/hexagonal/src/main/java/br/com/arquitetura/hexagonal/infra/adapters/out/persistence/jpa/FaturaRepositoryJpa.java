package br.com.arquitetura.hexagonal.infra.adapters.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepositoryJpa extends JpaRepository<FaturaEntityJpa, UUID> {
}

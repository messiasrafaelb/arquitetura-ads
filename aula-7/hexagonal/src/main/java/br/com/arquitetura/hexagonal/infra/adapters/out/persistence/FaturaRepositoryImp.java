package br.com.arquitetura.hexagonal.infra.adapters.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.arquitetura.hexagonal.domain.entities.Fatura;
import br.com.arquitetura.hexagonal.domain.ports.out.persistence.FaturaRepository;
import br.com.arquitetura.hexagonal.infra.adapters.out.persistence.jpa.FaturaEntityJpa;
import br.com.arquitetura.hexagonal.infra.adapters.out.persistence.jpa.FaturaRepositoryJpa;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FaturaRepositoryImp implements FaturaRepository {

    private final FaturaRepositoryJpa repositoryJpa;

    @Override
    public void salvar(Fatura fatura) {
        repositoryJpa.save(FaturaEntityJpa.doDominio(fatura));
    }

    @Override
    public Optional<Fatura> buscarPorId(UUID id) {
        return repositoryJpa.findById(id).map(FaturaEntityJpa::paraDominio);
    }
}

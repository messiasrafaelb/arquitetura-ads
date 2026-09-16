package br.com.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mvc.model.entity.Product;
import br.com.mvc.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository repository;

    public List<Product> read() {
        return repository.findAll();
    }
    
    public Product create(Product product) {
        return repository.save(product);
    }
}

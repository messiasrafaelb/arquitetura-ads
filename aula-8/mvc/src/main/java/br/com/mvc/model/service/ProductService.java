package br.com.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mvc.model.entity.Product;
import br.com.mvc.model.enums.EProductStatus;
import br.com.mvc.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository repository;

    public List<Product> read() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
    
    public Product create(Product product) {
        product.setStatus(EProductStatus.UNAVAILABLE);
        if (product.getQuantity() == null) {
            product.setQuantity(0);
        }
        return repository.save(product);
    }

    public Product discontinue(Long id) {
        Product product = findById(id);
        product.setStatus(EProductStatus.DISCONTINUED);
        return repository.save(product);
    }

    public Product entryStock(Long id, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantidade inválida para entrada");
        }
        Product product = findById(id);
        if (product.getStatus() == EProductStatus.DISCONTINUED) {
            throw new RuntimeException("Produto descontinuado não pode ser manipulado no estoque");
        }
        int current = product.getQuantity() == null ? 0 : product.getQuantity();
        product.setQuantity(current + quantity);
        if (product.getQuantity() > 0) {
            product.setStatus(EProductStatus.AVAILABLE);
        }
        return repository.save(product);
    }

    public Product exitStock(Long id, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantidade inválida para saída");
        }
        Product product = findById(id);
        if (product.getStatus() == EProductStatus.DISCONTINUED) {
            throw new RuntimeException("Produto descontinuado não pode ser manipulado no estoque");
        }
        int current = product.getQuantity() == null ? 0 : product.getQuantity();
        if (quantity > current) {
            throw new RuntimeException("Estoque insuficiente para saída");
        }
        product.setQuantity(current - quantity);
        if (product.getQuantity() == 0) {
            product.setStatus(EProductStatus.UNAVAILABLE);
        }
        return repository.save(product);
    }
}

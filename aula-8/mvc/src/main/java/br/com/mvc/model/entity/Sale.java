package br.com.mvc.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALE")
public class Sale {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "FATURA_ID")
    private String faturaId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}

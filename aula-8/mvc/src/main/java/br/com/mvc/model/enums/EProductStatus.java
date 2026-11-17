package br.com.mvc.model.enums;

import lombok.*;

@Getter
@AllArgsConstructor 
public enum EProductStatus {

    AVAILABLE("Disponível"),
    UNAVAILABLE("Indisponível"),
    DISCONTINUED("Descontinuado");

    public String description;
}
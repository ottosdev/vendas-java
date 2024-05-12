package com.vendas.vendas.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Table(name = "Produto")
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotEmpty(message = "Campo descricao obrigatorio")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "Preciso é obrigatorio")
    private BigDecimal preco;
}

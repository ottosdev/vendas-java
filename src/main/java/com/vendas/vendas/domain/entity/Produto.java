package com.vendas.vendas.domain.entity;

import jakarta.persistence.*;
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
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal preco;
}

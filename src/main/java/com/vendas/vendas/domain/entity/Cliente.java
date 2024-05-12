package com.vendas.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    @Column(length = 11)
    @CPF(message = "{campo.cpf.invalido}")
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    private String cpf;

    @Column(length = 100)
    @NotEmpty(message = "Campo é obrigatorio.")
    private String nome;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Pedido> pedidos;

}

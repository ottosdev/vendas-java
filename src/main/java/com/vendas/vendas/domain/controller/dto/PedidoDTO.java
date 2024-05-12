package com.vendas.vendas.domain.controller.dto;

import com.vendas.vendas.not.empty.list.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoDTO {
    @NotNull(message = "Informe o codigo do cliente")
    private Integer cliente;
    @NotNull(message = "Campo total é obrigatorio")
    private BigDecimal total;
    @NotEmptyList(message = "Pedido nao pode ser realizado sem items")
    private List<ItemPedidoDTO> itens;
}

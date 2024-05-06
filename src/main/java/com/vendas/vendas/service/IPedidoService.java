package com.vendas.vendas.service;

import com.vendas.vendas.domain.controller.dto.PedidoDTO;
import com.vendas.vendas.domain.entity.Pedido;
import com.vendas.vendas.domain.enums.StatusPedido;

import java.util.Optional;

public interface IPedidoService {

    Pedido salvar(PedidoDTO pedido);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}

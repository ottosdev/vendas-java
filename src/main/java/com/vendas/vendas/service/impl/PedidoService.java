package com.vendas.vendas.service.impl;

import com.vendas.vendas.domain.controller.dto.ItemPedidoDTO;
import com.vendas.vendas.domain.controller.dto.PedidoDTO;
import com.vendas.vendas.domain.entity.Cliente;
import com.vendas.vendas.domain.entity.ItemPedido;
import com.vendas.vendas.domain.entity.Pedido;
import com.vendas.vendas.domain.entity.Produto;
import com.vendas.vendas.domain.enums.StatusPedido;
import com.vendas.vendas.domain.repository.ClientesRepository;
import com.vendas.vendas.domain.repository.ItemPedidoRepository;
import com.vendas.vendas.domain.repository.PedidoRepository;
import com.vendas.vendas.domain.repository.ProdutoRepository;
import com.vendas.vendas.exception.PedidoNaoEncontradoException;
import com.vendas.vendas.exception.RegraNegocioException;
import com.vendas.vendas.service.IPedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoService implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Codigo do cliente invalido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIADO);
        List<ItemPedido> itemsPedidos = converterItems(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus( Integer id, StatusPedido statusPedido ) {
        pedidoRepository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatusPedido(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Nao é possivel realizar pedidos sem items");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtoRepository.findById(idProduto).orElseThrow(() ->
                    new RegraNegocioException("Produto invalido" + idProduto
                    ));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}

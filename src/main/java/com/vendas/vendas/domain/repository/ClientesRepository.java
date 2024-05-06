package com.vendas.vendas.domain.repository;

import com.vendas.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    @Query(value="select c from Cliente c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> encontrarPorNome( @Param("nome") String name);

    boolean existsByNome(String nome);

    @Query(value = "select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos( @Param("id") Integer id);
}

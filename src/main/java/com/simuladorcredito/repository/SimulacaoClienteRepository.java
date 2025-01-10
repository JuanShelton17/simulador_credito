package com.simuladorcredito.repository;

import com.simuladorcredito.model.SimulacaoCliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoClienteRepository extends MongoRepository<SimulacaoCliente, String> {
}

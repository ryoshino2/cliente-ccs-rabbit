package br.com.ryoshino.repository;

import br.com.ryoshino.model.ClienteCcsRabbit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteCcsRabbit, Long> {
    ClienteCcsRabbit findByIdCliente (Long idCliente);
}

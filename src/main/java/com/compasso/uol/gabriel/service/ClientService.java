package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import com.compasso.uol.gabriel.dto.client.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Client;

public interface ClientService {
	List<ReturnClientDTO> findAll();

	Optional<Client> findById(Long id);

	Optional<Client> findByCpf(String cpf);

	Client persist(Client client);

	void deleteById(Long id);
}

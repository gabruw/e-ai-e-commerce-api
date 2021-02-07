package com.compasso.uol.gabriel.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.compasso.uol.gabriel.dto.client.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Client;

public interface ClientService {
	Page<ReturnClientDTO> findAll(PageRequest pageRequest);

	Optional<Client> findById(Long id);

	Optional<Client> findByCpf(String cpf);

	Client persist(Client client);

	void deleteById(Long id);
}

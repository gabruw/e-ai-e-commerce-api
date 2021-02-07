package com.compasso.uol.gabriel.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.dto.client.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.repository.ClientRepository;
import com.compasso.uol.gabriel.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public Page<ReturnClientDTO> findAll(PageRequest pageRequest) {
		log.info("Buscando todos os clientes.");

		Page<Client> clients = this.clientRepository.findAll(pageRequest);
		return clients.map(client -> mapper.map(client, ReturnClientDTO.class));
	}

	@Override
	public Optional<Client> findById(Long id) {
		log.info("Buscando um cliente pelo Id: {}", id);
		return this.clientRepository.findById(id);
	}

	@Override
	public Optional<Client> findByCpf(String cpf) {
		log.info("Buscando um cliente pelo CPF: {}", cpf);
		return this.clientRepository.findByCpf(cpf);
	}

	@Override
	public Client persist(Client client) {
		log.info("Persistindo cliente: {}", client);
		return this.clientRepository.save(client);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Removendo um cliente pelo Id: {}", id);
		this.clientRepository.deleteById(id);
	}

}

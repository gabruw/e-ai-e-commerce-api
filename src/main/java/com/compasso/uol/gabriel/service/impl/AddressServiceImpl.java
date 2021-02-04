package com.compasso.uol.gabriel.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.dto.address.ReturnAddressDTO;
import com.compasso.uol.gabriel.entity.Address;
import com.compasso.uol.gabriel.repository.AddressRepository;
import com.compasso.uol.gabriel.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<ReturnAddressDTO> findAll() {
		log.info("Buscando todas os endereços.");

		List<Address> adresses = this.addressRepository.findAll();
		return adresses.stream().map(address -> mapper.map(address, ReturnAddressDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<Address> findByCep(String cep) {
		log.info("Buscando um endereço pelo CEP: {}", cep);
		return this.addressRepository.findByCep(cep);
	}

	@Override
	public Optional<Address> findById(Long id) {
		log.info("Buscando um endereço pelo Id: {}", id);
		return this.addressRepository.findById(id);
	}

	@Override
	public Address persist(Address address) {
		log.info("Persistindo endereço: {}", address);
		return this.addressRepository.save(address);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Removendo um endereço pelo Id: {}", id);
		this.addressRepository.deleteById(id);
	}
}

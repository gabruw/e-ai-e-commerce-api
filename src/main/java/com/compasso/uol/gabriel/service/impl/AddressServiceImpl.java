package com.compasso.uol.gabriel.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<ReturnAddressDTO> findAll(PageRequest pageRequest) {
		log.info("Buscando todas os endereços.");

		Page<Address> addresses = this.addressRepository.findAll(pageRequest);
		return addresses.map(address -> {
			ReturnAddressDTO returnAddress = mapper.map(address, ReturnAddressDTO.class);
			returnAddress.getCity().setAddresses(null);
			returnAddress.getCity().getState().setCities(null);

			return returnAddress;
		});
	}

	@Override
	public Page<Address> findByCep(String cep, Pageable pageable) {
		log.info("Buscando um endereço pelo CEP: {}", cep);

		Page<Address> addresses = this.addressRepository.findByCep(cep, pageable);
		return addresses.map(address -> {
			address.setClient(null);
			address.getCity().setAddresses(null);
			address.getCity().getState().setCities(null);

			return address;
		});
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

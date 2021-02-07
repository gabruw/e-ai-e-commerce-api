package com.compasso.uol.gabriel.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.compasso.uol.gabriel.dto.address.ReturnAddressDTO;
import com.compasso.uol.gabriel.entity.Address;

public interface AddressService {
	Page<ReturnAddressDTO> findAll(PageRequest pageRequest);

	Page<Address> findByCep(String cep, PageRequest pageRequest);

	Optional<Address> findById(Long id);

	Address persist(Address address);

	void deleteById(Long id);
}

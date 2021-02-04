package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import com.compasso.uol.gabriel.dto.address.ReturnAddressDTO;
import com.compasso.uol.gabriel.entity.Address;

public interface AddressService {
	List<ReturnAddressDTO> findAll();

	List<Address> findByCep(String cep);

	Optional<Address> findById(Long id);

	Address persist(Address address);

	void deleteById(Long id);
}

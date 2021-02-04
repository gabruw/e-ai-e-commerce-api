package com.compasso.uol.gabriel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Transactional(readOnly = true)
	Optional<List<Address>> findByCep(String cep);
}

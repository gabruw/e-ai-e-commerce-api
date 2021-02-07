package com.compasso.uol.gabriel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	@Transactional(readOnly = true)
	Page<Address> findByCep(String cep, PageRequest pageRequest);
}

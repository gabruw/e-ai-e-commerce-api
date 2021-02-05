package com.compasso.uol.gabriel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	@Transactional(readOnly = true)
	List<Address> findByCep(String cep);
}

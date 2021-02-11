package com.compasso.uol.gabriel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.compasso.uol.gabriel.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("select ars from address ars where ars.cep = :cep")
	Page<Address> findByCep(@Param("cep") String cep, Pageable pageable);
}

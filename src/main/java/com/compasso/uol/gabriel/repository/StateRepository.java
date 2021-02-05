package com.compasso.uol.gabriel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.uol.gabriel.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Transactional(readOnly = true)
	Optional<State> findByName(String name);
}

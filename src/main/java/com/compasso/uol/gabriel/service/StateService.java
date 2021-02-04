package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.state.ReturnStateDTO;
import com.compasso.uol.gabriel.entity.State;

public interface StateService {
	List<ReturnStateDTO> findAll();

	Optional<State> findById(Long id);

	Optional<State> findByName(String name);

	List<OptionDTO<Long>> findOptions();

	State persist(State state);

	void deleteById(Long id);
}
